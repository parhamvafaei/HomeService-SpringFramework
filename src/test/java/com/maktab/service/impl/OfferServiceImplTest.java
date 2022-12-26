package com.maktab.service.impl;

import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.ExpertService;
import com.maktab.service.OfferService;
import com.maktab.service.OrderService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void addNewOfferToOrder() {
        Order order = new Order();
        Expert expert = new Expert();
        expert.setPassword("Pksdjfj2");
        SubService subService = new SubService("sf", 45D, "adv", null, null);
        subServiceService.saveOrUpdate(subService);
        order.setSubService(subService);
        orderService.saveOrUpdate(order);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        expertService.saveOrUpdate(expert);
        Long aLong = service.addNewOfferToOrder(501D, Duration.ofHours(2L), expert, order);

        assertEquals(501D, service.findById(aLong).get().getPrice());
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

        Expert expert1=new Expert(1F,null,null,null,null);
        Expert expert2=new Expert(5F,null,null,null,null);
        Expert expert3=new Expert(2F,null,null,null,null);
        Expert expert4=new Expert(0F,null,null,null,null);
        expert1.setPassword("dgb2Afdr");
        expert2.setPassword("dgb2Afdr");
        expert3.setPassword("dgb2Afdr");
        expert4.setPassword("dgb2Afdr");


        Offer offer = new Offer();
        offer.setPrice(545D);
        offer.setExpert(expert1);
        Offer offer3 = new Offer();
        offer3.setPrice(85D);
        offer3.setExpert(expert2);
        Offer offer4 = new Offer();
        offer4.setPrice(0D);
        offer4.setExpert(expert3);
        Offer offer2 = new Offer();
        offer2.setPrice(96D);
        offer2.setExpert(expert4);
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
        System.out.println(service.offersToOrderByExpertRate(order.getId()));

        assertEquals(offer2.getExpert().getPassword(), service.offersToOrderByExpertRate(order.getId()).get(0).getExpert().getPassword());

    }
}