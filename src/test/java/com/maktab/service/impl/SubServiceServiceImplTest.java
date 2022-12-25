package com.maktab.service.impl;

import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.ExpertService;
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

    @Autowired
    private ExpertService expertService;

    @Test
    void checkSubServiceInName() {
        SubService subService1 = new SubService();
        service.saveOrUpdate(subService1);
        assertEquals(true, service.checkSubServiceInName(subService1.getName()));
    }
//#
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
        SubService subService = new SubService("subservice", 54D, "pppp", null, null);
        service.saveOrUpdate(subService);
        service.editSubService(subService.getId(), 48D, "oo");

        assertEquals(48D, service.findById(subService.getId()).get().getPrice());
        assertEquals("oo", service.findById(subService.getId()).get().getDescription());
    }

    //#
    @Test
    void addExpertToSubService() {
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        expert.setPassword("paRham23");
        SubService subService = new SubService();
        expertService.saveOrUpdate(expert);
        service.saveOrUpdate(subService);
        service.addExpertToSubService(expert, subService);

        assertTrue(service.findById(subService.getId()).get().getExperts().contains(expert));
    }

    @Test
    void deleteExpertOfSubService() {
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        SubService subService = new SubService();
    }

}