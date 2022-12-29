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
        Offer offer = new Offer();
        offer.setPrice(545D);
        Offer offer3 = new Offer();
        offer3.setPrice(85D);
        Offer offer4 = new Offer();
        offer4.setPrice(0D);
        Offer offer2 = new Offer();
        offer2.setPrice(96D);
        Order order = new Order();
        offer.setOrder(order);
        offer2.setOrder(order);
        offer3.setOrder(order);
        offer4.setOrder(order);
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


        Offer offer = new Offer();    offer.setPrice(545D);   offer.setExpert(expert1);
        Offer offer3 = new Offer();   offer3.setPrice(85D);   offer3.setExpert(expert2);
        Offer offer4 = new Offer();   offer4.setPrice(0D);    offer4.setExpert(expert3);
        Offer offer2 = new Offer();   offer2.setPrice(96D);   offer2.setExpert(expert4);

        Order order = new Order();
        offer.setOrder(order);
        offer2.setOrder(order);
        offer3.setOrder(order);
        offer4.setOrder(order);
        orderService.saveOrUpdate(order);
        service.saveOrUpdate(offer);
        service.saveOrUpdate(offer2);
        service.saveOrUpdate(offer3);
        service.saveOrUpdate(offer4);


        assertEquals(offer2.getExpert().getPassword(), service.offersToOrderByExpertRate(order.getId()).get(0).getExpert().getPassword());

    }
}