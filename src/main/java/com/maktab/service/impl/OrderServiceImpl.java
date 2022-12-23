package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.*;
import com.maktab.exception.OrderStatusConditionException;
import com.maktab.repository.OrderRepository;
import com.maktab.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository) {
        super(repository);
    }

    @Transactional
    @Override
    public void addOrder(Double price, String description, LocalDateTime time, Address address, SubService subService) {
        Order order = new Order(price, description, time, address);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        order.setSubService(subService);

        saveOrUpdate(order);

    }

    //    save Expert to order
    @Transactional
    @Override
    public void chooseExpert(Long id, Offer offer) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        if (findById(id).get().getOrderStatus().equals(OrderStatus.SELECTING_EXPERT)) {
            order.setOrderStatus(OrderStatus.WAITING_EXPERT_COME);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException();
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
    public void changeOrderStatusToDone(Long id) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
            order.setIsDone(true);
            saveOrUpdate(order);
        } else
            throw new OrderStatusConditionException();
    }
}
