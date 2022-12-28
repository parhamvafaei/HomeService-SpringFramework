package com.maktab.service.impl;


import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.FileReaderException;
import com.maktab.service.ExpertService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ImportResource;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpertServiceImplTest {

    @Autowired
    private ExpertService service;

    @Autowired
    private SubServiceService subServiceService;

    @Test
    void changePassword() {
        Expert expert = new Expert();
        expert.setPassword("Pksdjfj2");
        service.saveOrUpdate(expert);
        service.changePassword(expert.getId(), "FGsrofm3");


        assertEquals("FGsrofm3", service.findById(expert.getId()).get().getPassword());
    }


    @Test
    void setProfileImage() throws IOException {
        Expert expert = new Expert();
        expert.setPassword("Pksdjfj2");
        service.saveOrUpdate(expert);
        File image = new File("IMG_20220225_174859_946.jpg");
        byte[] bytes;
        if (image.getName().contains(".jpg")) {
            try (FileInputStream reader = new FileInputStream(image)) {
                bytes = reader.readAllBytes();
            } catch (Exception e) {
                throw new FileReaderException();
            }
        } else
            throw new FileReaderException("wrong file format !");
        service.setProfileImage(bytes, expert.getId());

//        FileInputStream reader = new FileInputStream(file);
//        byte[] bytes = reader.readAllBytes();

        assertArrayEquals(service.findById(expert.getId()).get().getImage(), bytes);
    }

    @Test
    void confirmExpert() {
        Expert expert = new Expert();
        expert.setPassword("Pksdjfj2");
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        service.saveOrUpdate(expert);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(expert.getId()).get().getExpertStatus());
    }

    @Test
    void addExpertToSubService() {
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        expert.setPassword("paRham23");
        SubService subService = new SubService();
        subService.setName("sub");
        service.saveOrUpdate(expert);
        subServiceService.saveOrUpdate(subService);
        service.addExpertToSubService(expert, subService);
        System.out.println(subServiceService.findById(subService.getId()).get().getExperts());
        System.out.println(service.findById(expert.getId()).get().getSubServices());

        assertEquals(subService.getName(),service.findById(expert.getId()).get().getSubServices().get(0).getName());
    }

    @Test
    void deleteExpertOfSubService() {
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        expert.setPassword("paRham23");
        SubService subService = new SubService();
        subService.setName("sub");
        service.saveOrUpdate(expert);
        subServiceService.saveOrUpdate(subService);
        service.addExpertToSubService(expert, subService);
        service.deleteExpertOfSubService(expert,subService);

        assertThrows(NullPointerException.class,this::deleteExpertOfSubService);

    }

}