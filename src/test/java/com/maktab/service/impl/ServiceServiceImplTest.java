package com.maktab.service.impl;

import com.maktab.entity.Service;
import com.maktab.service.ServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ServiceServiceImplTest {
    @Autowired
    private ServiceService service;

    @Test
    @Order(1)
    void addService() {
        Long id = service.addService("service");
        assertEquals("service",service.findById(id).get().getName());
    }

    @Test
    @Order(2)

    void loadServices() {
        Service service1=new Service();
        Service service2=new Service();
        service.saveOrUpdate(service1);
        service.saveOrUpdate(service2);

        assertEquals(2,service.loadServices().size());
    }
}