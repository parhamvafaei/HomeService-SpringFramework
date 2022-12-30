package com.maktab.service.impl;

import com.maktab.entity.*;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.ExpertService;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest {


    @Autowired
    private OrderService orderService;

    @Autowired
    private SubServiceService subServiceService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ExpertService expertService;

    @Test
    @org.junit.jupiter.api.Order(1)
    void addOrder() {
        SubService subService1 = SubService.builder().name("subService").price(45D).build();
        subServiceService.saveOrUpdate(subService1);
        Long sf = orderService.addOrder(46D, "none", LocalDateTime.now(), null, subService1);

        assertEquals(subService1.getName(), orderService.findById(sf).get().getSubService().getName());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void chooseExpert() {
        Order order = orderService.findById(2L).get();
        order.setOrderStatus(OrderStatus.SELECTING_EXPERT);

        Offer offer = new Offer(2D, Duration.ofHours(2), false, null, order);
        offerService.saveOrUpdate(offer);
        orderService.saveOrUpdate(order);
        orderService.selectExpertToOrder(offer.getId(), order.getId());

        assertEquals(OrderStatus.WAITING_EXPERT_COME, orderService.findById(order.getId()).get().getOrderStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void changeOrderStatusToStarted() {

        Order order = orderService.findById(2L).get();
        order.setOrderStatus(OrderStatus.WAITING_EXPERT_COME);

        orderService.saveOrUpdate(order);
        orderService.changeOrderStatusToStarted(order.getId());

        assertEquals(OrderStatus.STARTED, orderService.findById(order.getId()).get().getOrderStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void changeOrderStatusToDone() {
        Order order = orderService.findById(2L).get();
        order.setOrderStatus(OrderStatus.STARTED);

        orderService.saveOrUpdate(order);
        orderService.changeOrderStatusToDone(order.getId());

        assertEquals(OrderStatus.DONE, orderService.findById(order.getId()).get().getOrderStatus());
    }

    @Transactional
    @Test
    @org.junit.jupiter.api.Order(4)
    void showRelatedOrdersBySubService() {
        SubService subService = subServiceService.findById(1L).get();
        Order order = orderService.findById(2L).get();
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        order.setSubService(subService);
        subServiceService.saveOrUpdate(subService);
        Expert expert = Expert.builder().password("Parham12").expertStatus(ExpertStatus.CONFIRMED).build();
        List<SubService> list = expert.getSubServices();
        list.add(subService);
        expert.setSubServices(list);
        expertService.saveOrUpdate(expert);

        assertEquals(1, orderService.showRelatedOrdersBySubService(expert.getId()).size());

    }
}
