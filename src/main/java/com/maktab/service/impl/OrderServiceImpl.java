package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.*;
import com.maktab.entity.dto.AddressDTO;
import com.maktab.entity.dto.OrderFilter;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.*;
import com.maktab.repository.OrderRepository;
import com.maktab.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRepository> implements OrderService {

    @PersistenceContext
    private EntityManager em;

    private final ExpertService expertService;
    private final OfferService offerService;

    private final ClientService clientService;

    public OrderServiceImpl(OrderRepository repository, @Lazy ExpertService expertService, @Lazy OfferService offerService, ClientService clientService) {
        super(repository);
        this.expertService = expertService;
        this.offerService = offerService;
        this.clientService = clientService;
    }

    @Transactional
    @Override
    public Long addOrder(Double price, String description, LocalDateTime time, AddressDTO address, SubService subService) {
        Address address1 = Address.builder().address(address.getAddress()).phoneNumber(address.getPhoneNumber()).build();
        Order order = new Order(price, description, time, address1);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        order.setSubService(subService);

        saveOrUpdate(order);
        return order.getId();
    }

    @Transactional
    @Override
    public void selectExpertToOrder(Long offerId, Long orderId) {

        Order order = findById(orderId).orElseThrow(() -> new NullPointerException("cant invoke order"));
        Offer offer = offerService.findById(offerId).orElseThrow(() -> new NullPointerException("cant invoke order"));
        if (!(offer.getOrder().getId().equals(order.getId())))
            throw new SelectOrderException("offer doesnt match this order");
        if (!(order.getOrderStatus().equals(OrderStatus.SELECTING_EXPERT)))
            throw new ExpertConditionException("wrong order status");
        order.setTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.WAITING_EXPERT_COME);
        offer.setSet(true);
        offerService.saveOrUpdate(offer);
        saveOrUpdate(order);
    }


    @Transactional
    @Override
    public void changeOrderStatusToStarted(Long id) {
        Order order = findById(id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        if (order.getOrderStatus().equals(OrderStatus.WAITING_EXPERT_COME)) {
            order.setOrderStatus(OrderStatus.STARTED);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException("wrong order status");
    }

    @Transactional
    @Override
    public void changeOrderStatusToDone(Long id, Duration time) {
        Order order = findById(id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
            order.setIsDone(true);
            order.setActualDurationTime(time);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException("wrong order status");
    }

    @Override
    public List<Order> showRelatedOrdersBySubService(Long expertId) {
        Expert expert = expertService.findById(expertId).orElseThrow(() -> new NotFoundPersonException("cant invoke expert"));

        List<Order> relatedList = new ArrayList<>();
        List<SubService> subServices = expert.getSubServices();
        subServices.forEach(subService -> relatedList.addAll(subService.getOrders()));


        return relatedList.stream()
                .filter(order -> order
                        .getOrderStatus()
                        .equals(OrderStatus.WAITING_FOR_EXPERT))
                .toList();

    }

    @Transactional
    @Override
    public void setComment(Comment comment, Long order_id) {
        Order order = findById(order_id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        Expert expert = findExpert(order_id);
        if (!(order.getOrderStatus() == OrderStatus.DONE || order.getOrderStatus() == OrderStatus.PAID))
            throw new OrderStatusConditionException("wrong order status");
        if (comment.getRating() == null)
            throw new NullPointerException("found rating null for commenting");
        if (!(comment.getRating() >= 0 || comment.getRating() <= 5))
            throw new ValidationException("score is not in range");

        expertAccountStatus(order_id, expert.getId());
        setExpertScore(order_id, comment.getRating());
        order.setComment(comment);
        saveOrUpdate(order);

    }


    @Override
    public Float showCommentRatingToExpert(Long id, Long expert_id) {
        Order order = findById(id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        Expert expert = expertService.findById(expert_id).orElseThrow(() -> new NullPointerException("cant invoke expert"));
        if (!(order.getOrderStatus() == OrderStatus.DONE || order.getOrderStatus() == OrderStatus.PAID))
            throw new OrderStatusConditionException("wrong order status");
        boolean empty = expert.getOffers().stream().filter(offer ->
                Objects.equals(offer.getOrder().getId(), order.getId())
                        &&
                        offer.isSet()).findAny().isEmpty();
        if (empty == true)
            throw new ValidationException("not found any suitable offer");

        return order.getComment().getRating();
    }

    @Transactional
    @Override
    public void expertAccountStatus(Long orderId, Long expert_id) {
        Order order = findById(orderId).orElseThrow(() -> new NullPointerException("cant invoke order"));
        Expert expert = expertService.findById(expert_id).orElseThrow(() -> new NullPointerException("cant invoke expert"));
        if (expert.getRating() < 0) {
            expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
            expertService.saveOrUpdate(expert);
            throw new ExpertConditionException("expert status become from start because of minus rating");
        }
        if (!(order.getOrderStatus() == OrderStatus.DONE))
            throw new OrderStatusConditionException("wrong order status");
        Optional<Offer> offer1 = expert.getOffers().stream().filter(offer -> offer.isSet() == true).findAny();
        if (offer1.isEmpty())
            throw new NullPointerException("not found any done offer");

        long time = offer1.get().getDurationTime().toHours();
        long actualTime = order.getActualDurationTime().toHours();
        long between = time - actualTime;

        if (between > 0) {
            if (expert.getRating() - between > 0) {
                expert.setRating(expert.getRating() - between);
            } else {
                expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
            }
            expertService.saveOrUpdate(expert);
        }
    }

    @Override
    public Expert findExpert(Long order_id) {

        Order order = findById(order_id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        return offerService.findAll().stream().filter(offer ->
                        (offer.isSet()) && (offer.getOrder().equals(order)))
                .findAny().get().getExpert();

    }

    @Transactional
    @Override
    public void payFromCredit(Long order_id, Long client_id) {
        Order order = findById(order_id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        Client client = clientService.findById(client_id).orElseThrow(() -> new NullPointerException("cant invoke client"));
        if (client.getCredit().getAmount() < order.getPrice())
            throw new NotEnoughMoneyException("not enough budget");
        if (order.getOrderStatus() == OrderStatus.DONE) {
            order.setOrderStatus(OrderStatus.PAID);
            Credit credit = client.getCredit();
            credit.setAmount(credit.getAmount() - order.getPrice());
            Expert expert = findExpert(order_id);
            expert.setTotalMoney(expert.getTotalMoney() + ((order.getPrice() * 70) / 100));

            saveOrUpdate(order);
            clientService.saveOrUpdate(client);
            expertService.saveOrUpdate(expert);
        } else
            throw new OrderStatusConditionException("wrong order status");

    }

    @Transactional
    @Override
    public void payFromCard(Long order_id) {
        Order order = findById(order_id).orElseThrow(() -> new NullPointerException("cant invoke order"));
        if (order.getOrderStatus() == OrderStatus.DONE) {
            order.setOrderStatus(OrderStatus.PAID);
            Expert expert = findExpert(order_id);
            expert.setTotalMoney(expert.getTotalMoney() + ((order.getPrice() * 70) / 100));
            saveOrUpdate(order);
            expertService.saveOrUpdate(expert);
        }
    }

    @Transactional
    @Override
    public void setExpertScore(Long order_id, Float rating) {
        Expert expert = findExpert(order_id);

        int size = offerService.findAll().stream().filter(offer ->
                (offer.isSet())
                        && offer.getExpert().equals(expert)
                        && offer.getOrder().getComment().getRating() != null).toList().size();
        if (size != 0) {
            float calculate = (expert.getRating() * size) + rating;
            expert.setRating(calculate / size + 1);
            expertService.saveOrUpdate(expert);
        }
    }


    @Override
    public List<Order> filterOrderHistory(OrderFilter orderFilter) {

        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        predicateList.add(criteriaBuilder.equal(root.get("isDone"), true));
        if (orderFilter.getStartTime() == null && orderFilter.getEndTime() != null) {
            predicateList.add(criteriaBuilder.between(root.get("createTime"), LocalDateTime.of(2000, 2, 7,2,2), orderFilter.getEndTime()));
        }

        if (orderFilter.getStartTime() != null && orderFilter.getEndTime() == null) {
            predicateList.add(criteriaBuilder.between(root.get("createTime"), orderFilter.getStartTime(), LocalDateTime.now()));
        }

        if (orderFilter.getStartTime() != null && orderFilter.getEndTime() != null) {
            predicateList.add(criteriaBuilder.between(root.get("createTime"), orderFilter.getStartTime(), orderFilter.getEndTime()));
        }

        if (orderFilter.getOrderStatus() != null) {

            predicateList.add(criteriaBuilder.equal(root.get("orderStatus"), orderFilter.getOrderStatus()));
        }

        if (orderFilter.getSubService() != null) {

            predicateList.add(criteriaBuilder.like(root.get("subService").get("name"), "%" + orderFilter.getSubService() + "%"));
        }

        if (orderFilter.getService() != null) {

            predicateList.add(criteriaBuilder.like(root.get("service").get("name"), "%" + orderFilter.getService() + "%"));
        }

        Predicate[] predicateArray = new Predicate[predicateList.size()];
        predicateList.toArray(predicateArray);
        query.select(root).where(predicateArray);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Order> expertOrders(Long expert_id, String orderStatus) {
        return repository.expertOrders(expert_id, OrderStatus.valueOf(orderStatus.toUpperCase()));
    }

    @Override
    public List<Order> clientOrders(Long client_id, String orderStatus) {
        return repository.clientOrders(client_id, OrderStatus.valueOf(orderStatus.toUpperCase()));
    }
}
