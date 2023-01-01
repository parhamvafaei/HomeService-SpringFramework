package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.*;
import com.maktab.entity.dto.AddressDTO;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.*;
import com.maktab.repository.OrderRepository;
import com.maktab.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRepository> implements OrderService {

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
        Address address1= Address.builder().address(address.getAddress()).phoneNumber(address.getPhoneNumber()).build();
        Order order = new Order(price, description, time, address1);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        order.setSubService(subService);

        saveOrUpdate(order);
        return order.getId();
    }

    @Transactional
    @Override
    public void selectExpertToOrder(Long offerId, Long orderId) {

        Order order = findById(orderId).orElseThrow(NullPointerException::new);
        Offer offer = offerService.findById(offerId).orElseThrow(NullPointerException::new);
        if (!(offer.getOrder().getId().equals(order.getId())))
            throw new SelectOrderException();
        if (!(order.getOrderStatus().equals(OrderStatus.SELECTING_EXPERT)))
            throw new ExpertConditionException();
        order.setTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.WAITING_EXPERT_COME);
        offer.setSet(true);
        offerService.saveOrUpdate(offer);
        saveOrUpdate(order);
    }


    @Transactional
    @Override
    public void changeOrderStatusToStarted(Long id) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        if (order.getOrderStatus().equals(OrderStatus.WAITING_EXPERT_COME)) {
            order.setOrderStatus(OrderStatus.STARTED);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException();
    }

    @Transactional
    @Override
    public void changeOrderStatusToDone(Long id, Duration time) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
            order.setIsDone(true);
            order.setActualDurationTime(time);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException();
    }

    @Override
    public List<Order> showRelatedOrdersBySubService(Long expertId) {
        Expert expert = expertService.findById(expertId).orElseThrow(NotFoundPersonException::new);

        List<Order> relatedList = new ArrayList<>();
        List<SubService> subServices = expert.getSubServices();
        subServices.forEach(subService -> relatedList.addAll(subService.getOrders()));


        return relatedList.stream()
                .filter(order -> order
                        .getOrderStatus()
                        .equals(OrderStatus.WAITING_FOR_EXPERT))
                .toList();

    }

    //offer.isSet()!
    @Transactional
    @Override
    public void setComment(Comment comment, Long expert_id) {
        Order order = findById(expert_id).orElseThrow(NullPointerException::new);
        if (!(order.getOrderStatus() == OrderStatus.DONE || order.getOrderStatus() == OrderStatus.PAID))
            throw new OrderStatusConditionException();
        if (comment.getRating() == null)
            throw new NullPointerException();
        if (comment.getRating() >= 0 || comment.getRating() <= 5)
            throw new ValidationException("score is not in range");

        order.setComment(comment);
        saveOrUpdate(order);

    }


    @Override
    public Float showCommentRatingToExpert(Long id, Long expert_id) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        Expert expert = expertService.findById(expert_id).orElseThrow(NullPointerException::new);
        if (!(order.getOrderStatus() == OrderStatus.DONE || order.getOrderStatus() == OrderStatus.PAID))
            throw new OrderStatusConditionException();
        boolean empty = expert.getOffers().stream().filter(offer ->
                Objects.equals(offer.getOrder().getId(), order.getId())
                        &&
                        offer.isSet()).findAny().isEmpty();
        if (empty == true)
            throw new ValidationException();

        return order.getComment().getRating();
    }

    @Transactional
    @Override
    public void expertAccountStatus(Long orderId, Long expert_id) {
        Order order = findById(orderId).orElseThrow(NullPointerException::new);
        Expert expert = expertService.findById(expert_id).orElseThrow(NullPointerException::new);
        if (expert.getRating() < 0) {
            expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
            expertService.saveOrUpdate(expert);
            throw new ExpertConditionException();
        }
        if (!(order.getOrderStatus() == OrderStatus.DONE))
            throw new OrderStatusConditionException();
        Optional<Offer> offer1 = expert.getOffers().stream().filter(offer -> offer.isSet() == true).findAny();
        if (offer1.isEmpty())
            throw new NullPointerException();

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
    public Expert findExpert(Long order_id, Long offer_id) {

        Order order = findById(order_id).orElseThrow(NullPointerException::new);
        return offerService.findAll().stream().filter(offer ->
                        (offer.isSet()) && (offer.getOrder().equals(order)))
                .findAny().get().getExpert();

    }

    @Transactional
    @Override
    public void payFromCredit(Long order_id, Long client_id) {
        Order order = findById(order_id).orElseThrow(NullPointerException::new);
        Client client = clientService.findById(client_id).orElseThrow(NullPointerException::new);
        if (client.getCredit().getAmount() < order.getPrice())
            throw new NotEnoughMoneyException();
        if (order.getOrderStatus() == OrderStatus.DONE) {
            order.setOrderStatus(OrderStatus.PAID);
            Credit credit = client.getCredit();
            credit.setAmount(credit.getAmount() - order.getPrice());

            saveOrUpdate(order);
            clientService.saveOrUpdate(client);
        } else
            throw new OrderStatusConditionException();

    }
}
