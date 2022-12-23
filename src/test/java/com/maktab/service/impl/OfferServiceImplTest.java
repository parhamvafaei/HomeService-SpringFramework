package com.maktab.service.impl;

import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OfferServiceImplTest {

    @Autowired
private OfferService service;
    @Autowired
    private OrderService orderService;

    @Autowired
    private SubServiceService subServiceService;

    @Test
    void addNewOfferToOrder() {
        Order order=new Order();
        Expert expert=new Expert();
        SubService subService=new SubService();
        subServiceService.saveOrUpdate(subService);
        subService.setPrice(500D);
        order.setSubService(subService);
        orderService.saveOrUpdate(order);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        Long aLong = service.addNewOfferToOrder(501D, Duration.ofHours(2L), expert, order);

        assertEquals(500D,service.findById(aLong).get().getPrice());
    }


    @Test
    void offersToOrderByPrice() {

    }


    @Test
    void offersToOrderByExpertRate() {

    }


}