package com.maktab.service.impl;

import com.maktab.entity.Service;
import com.maktab.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceServiceImplTest {
    @Autowired
    private ServiceService service;

    @Test
    void loadServices() {
        Service service1=new Service();
        Service service2=new Service();
        Service service3=new Service();
        service.saveOrUpdate(service1);
        service.saveOrUpdate(service2);

        assertEquals(2,service.loadServices().size());
    }
}