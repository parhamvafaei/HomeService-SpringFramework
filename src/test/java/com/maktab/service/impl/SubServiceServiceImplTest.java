package com.maktab.service.impl;

import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.service.ServiceService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class SubServiceServiceImplTest {

    @Autowired
    private SubServiceService service;

    @Autowired
    private ServiceService serviceService;


    @Test
    @Order(1)
    void addSubService() {
        Service service1 = new Service();
        service1.setName("work");
        serviceService.saveOrUpdate(service1);
        SubService subService1 = SubService.builder().name("subService").price(2D).description("none").build();
        service.saveOrUpdate(subService1);

        Long aLong = service.addSubService(subService1, service1);

        assertEquals(service1.getName(), service.findById(aLong).get().getService().getName());
    }

    @Test
    @Order(2)

    void editSubService() {
        SubService subService = service.findById(2L).get();
        service.editSubService(2L, 48D, "oo");

        assertEquals(48D, service.findById(subService.getId()).get().getPrice());
        assertEquals("oo", service.findById(subService.getId()).get().getDescription());
    }

    @Test
    @Order(3)

    void checkSubServiceInName() {

        assertEquals(true, service.checkSubServiceByName("subService"));
    }

    @Test
    @Order(4)

    void loadSubServices() {
        SubService subService1 = new SubService();
        service.saveOrUpdate(subService1);

        assertEquals(2, service.loadSubServices().size());
    }



}