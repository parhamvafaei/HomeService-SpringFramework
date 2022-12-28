package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.*;
import com.maktab.entity.person.Expert;
import com.maktab.exception.NotFoundPersonException;
import com.maktab.exception.OrderStatusConditionException;
import com.maktab.repository.OrderRepository;
import com.maktab.service.ExpertService;
import com.maktab.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, OrderRepository> implements OrderService {

    private final ExpertService expertService;
    public OrderServiceImpl(OrderRepository repository, ExpertService expertService) {
        super(repository);
        this.expertService = expertService;
    }

    @Transactional
    @Override
    public Long addOrder(Double price, String description, LocalDateTime time, Address address, SubService subService) {
        Order order = new Order(price, description, time, address);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        order.setSubService(subService);

        saveOrUpdate(order);
return order.getId();
    }

    //    save Expert to order
    @Transactional
    @Override
    public void chooseExpert(Long id) {
        Order order = findById(id).orElseThrow(NullPointerException::new);
        if (order.getOrderStatus().equals(OrderStatus.SELECTING_EXPERT)) {
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

    @Override
    public List<Order> showRelatedOrdersBySubService(Long expertId) {
        Expert expert = expertService.findById(expertId).orElseThrow(NotFoundPersonException::new);
        List<Order> result =new ArrayList<>();
        List<SubService> subServices = expert.getSubServices();
        subServices.forEach(subService -> result.addAll(subService.getOrders()));
        return result;

    }

@Override
    public  List<Order> findOrdersToOffer() {
        return findAll().stream()
                .filter(order -> order
                        .getOrderStatus()
                        .equals(OrderStatus.WAITING_FOR_EXPERT))
                .toList();

    }

}
