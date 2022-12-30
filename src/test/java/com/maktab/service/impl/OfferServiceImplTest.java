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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfferServiceImplTest {

    @Autowired
    private OfferService service;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private SubServiceService subServiceService;

    @Test
    @org.junit.jupiter.api.Order(1)
    void addNewOfferToOrder() {
        SubService subService =SubService.builder().name("subService").price(45D).build();
        subServiceService.saveOrUpdate(subService);
        Order order = new Order(46D,"none", LocalDateTime.now(),null);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT);
        Expert expert = Expert.builder().password("Parham21").expertStatus(ExpertStatus.CONFIRMED).build();
        expertService.saveOrUpdate(expert);
        order.setSubService(subService);
        orderService.saveOrUpdate(order);
        expertService.saveOrUpdate(expert);
        Long aLong = service.addNewOfferToOrder(501D, Duration.ofHours(2), expert, order);

        assertEquals(order.getId(), service.findById(aLong).get().getOrder().getId());
    }


    @Test

    void offersToOrderByPrice() {
        Order order = new Order();

        Offer offer = Offer.builder().price(545D).order(order).build();
        Offer offer3 =  Offer.builder().price(85D).order(order).build();
        Offer offer4 =Offer.builder().price(0D).order(order).build();
        Offer offer2 =Offer.builder().price(96D).order(order).build();

        orderService.saveOrUpdate(order);
        service.saveOrUpdate(offer);
        service.saveOrUpdate(offer2);
        service.saveOrUpdate(offer3);
        service.saveOrUpdate(offer4);


        assertEquals(offer4.getPrice(), service.offersToOrderByPrice(order.getId()).get(0).getPrice());


    }


    @Test
    void offersToOrderByExpertRate() {

        Expert expert1 =Expert.builder().rating(1F).password("Parham12").build();
        Expert expert2 =Expert.builder().rating(5F).password("Parham12").build();
        Expert expert3 = Expert.builder().rating(2F).password("Parham12").build();
        Expert expert4 =Expert.builder().rating(0F).password("Parham12").build();
        expertService.saveOrUpdate(expert1);
        expertService.saveOrUpdate(expert2);
        expertService.saveOrUpdate(expert3);
        expertService.saveOrUpdate(expert4);
        Order order = new Order();


        Offer offer = Offer.builder().price(545D).expert(expert1).order(order).build();
        Offer offer3 =  Offer.builder().price(85D).expert(expert2).order(order).build();
        Offer offer4 =Offer.builder().price(0D).expert(expert3).order(order).build();
        Offer offer2 =Offer.builder().price(96D).expert(expert4).order(order).build();

        orderService.saveOrUpdate(order);
        service.saveOrUpdate(offer);
        service.saveOrUpdate(offer2);
        service.saveOrUpdate(offer3);
        service.saveOrUpdate(offer4);


        assertEquals(offer2.getExpert().getPassword(), service.offersToOrderByExpertRate(order.getId()).get(0).getExpert().getPassword());

    }
}