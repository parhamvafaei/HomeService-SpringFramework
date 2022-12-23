package com.maktab.service.impl;

import com.maktab.entity.*;
import com.maktab.entity.person.Expert;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {


    @Autowired
    private OrderService orderService;

    @Autowired
    private SubServiceService subServiceService;

    @Autowired
    private OfferService offerService;
    @Test
    void addOrder() {


        SubService subService1=new SubService();
        subService1.setPrice(45D);
        subService1.setName("aaa");
        subServiceService.saveOrUpdate(subService1);
        Long sf = orderService.addOrder(46D, "sf", LocalDateTime.now(), null, subService1);

        assertEquals(subService1.getName(),orderService.findById(sf).get().getSubService().getName());
    }

    @Test
    void chooseExpert() {
        Order order=new Order(45D,"cvbgsfv",LocalDateTime.now(),null);
        order.setOrderStatus(OrderStatus.SELECTING_EXPERT);

        orderService.saveOrUpdate(order);
        orderService.chooseExpert(order.getId());

        assertEquals(OrderStatus.WAITING_EXPERT_COME,orderService.findById(order.getId()).get().getOrderStatus());
    }

    @Test
    void changeOrderStatusToStarted() {

        Order order=new Order(45D,"cvbgsfv",LocalDateTime.now(),null);
        order.setOrderStatus(OrderStatus.WAITING_EXPERT_COME);

        orderService.saveOrUpdate(order);
        orderService.changeOrderStatusToStarted(order.getId());

        assertEquals(OrderStatus.STARTED,orderService.findById(order.getId()).get().getOrderStatus());
    }

    @Test
    void changeOrderStatusToDone() {
        Order order=new Order(45D,"cvbgsfv",LocalDateTime.now(),null);
        order.setOrderStatus(OrderStatus.STARTED);

        orderService.saveOrUpdate(order);
        orderService.changeOrderStatusToDone(order.getId());

        assertEquals(OrderStatus.DONE,orderService.findById(order.getId()).get().getOrderStatus());
    }
}