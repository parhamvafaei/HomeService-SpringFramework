package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import com.maktab.entity.OrderStatus;

import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.ExpertConditionException;

import com.maktab.exception.OrderStatusConditionException;
import com.maktab.exception.SelectOrderException;
import com.maktab.repository.OfferRepository;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Service
public class OfferServiceImpl extends BaseServiceImpl<Offer, OfferRepository> implements OfferService {

    private final OrderService orderService;

    public OfferServiceImpl(OfferRepository repository, OrderService orderService) {
        super(repository);
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public Long addNewOfferToOrder(Double price, Duration durationTime, Expert expert, Order order) {
        if (!(order.getOrderStatus() == OrderStatus.WAITING_FOR_EXPERT))
            throw new OrderStatusConditionException("wrong order status");
        if (!(expert.getSubServices().contains(order.getSubService())))
            throw new ExpertConditionException("order is not in your subService");
        if (!(expert.getExpertStatus().equals(ExpertStatus.CONFIRMED)))
            throw new ExpertConditionException("wrong expert status");

        if (order.getSubService().getPrice() > price)
            throw new ValidationException();


        Offer offer = new Offer(price, durationTime,false, expert, order);
        order.setOrderStatus(OrderStatus.SELECTING_EXPERT);
        saveOrUpdate(offer);
        return offer.getId();

    }

    @Override
    public List<Offer> offersToOrderByPrice(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new NullPointerException("given order not found");
        List<Offer> offerList = repository.findByOrderId(orderId);

        offerList.sort(Comparator.comparingDouble(Offer::getPrice));
        return offerList;
    }

    @Override
    public List<Offer> offersToOrderByExpertRate(Long orderId) {
        if (orderService.findById(orderId).isEmpty())
            throw new NullPointerException("given order not found");
        List<Offer> offerList = repository.findByOrderId(orderId);
        offerList.sort((o1, o2) -> (int) (o1.getExpert().getRating() - o2.getExpert().getRating()));
        return offerList;
    }
}
