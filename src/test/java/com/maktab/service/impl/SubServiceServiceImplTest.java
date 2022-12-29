package com.maktab.service.impl;

import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.service.ServiceService;
import com.maktab.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
@SpringBootTest
class SubServiceServiceImplTest {

    @Autowired
    private SubServiceService service;

    @Autowired
    private ServiceService serviceService;


    @Test
    void checkSubServiceInName() {
        SubService subService1 = new SubService();
        service.saveOrUpdate(subService1);
        assertEquals(true, service.checkSubServiceByName(subService1.getName()));
    }

    @Test
    void addSubService() {
        Service service1 = new Service();
        service1.setName("work");
        serviceService.saveOrUpdate(service1);
        SubService subService1 = new SubService();
        service.saveOrUpdate(subService1);

        Long aLong = service.addSubService(subService1, service1);

        assertEquals(service1.getName(), service.findById(aLong).get().getService().getName());
    }

    @Test
    void loadSubServices() {
        SubService subService1 = new SubService();
        SubService subService2 = new SubService();
        service.saveOrUpdate(subService1);
        service.saveOrUpdate(subService2);

        assertEquals(2, service.LoadSubServices().size());
    }

    @Test
    void editSubService() {
        SubService subService = new SubService("subservice", 54D, "pppp", null, null,null);
        service.saveOrUpdate(subService);
        service.editSubService(subService.getId(), 48D, "oo");

        assertEquals(48D, service.findById(subService.getId()).get().getPrice());
        assertEquals("oo", service.findById(subService.getId()).get().getDescription());
    }



}