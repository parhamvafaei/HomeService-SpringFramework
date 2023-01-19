package com.maktab.service.impl;


import com.maktab.entity.Service;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.exception.FileReaderException;
import com.maktab.service.ExpertService;
import com.maktab.service.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ImportResource;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceImplTest {

    @Autowired
    private ExpertService service;

    @Autowired
    private SubServiceService subServiceService;

    @Test
    @Order(1)
    void signIn() {
        String s = service.signIn("parham", "vafaei", "parhm@gmail.com", "Parham12", null);
        assertEquals(1L, s);
//unDone
    }

    @Test
    @Order(2)
    void changePassword() {

        service.changePassword(1L, "Aaaaaaa1");


        assertEquals("Aaaaaaa1", service.findById(1L).get().getPassword());
    }


    @Test
    @Order(3)
    void setProfileImage() throws IOException {

        File image = new File("IMG_20220225_174859_946.jpg");
        byte[] bytes;
        String[] split = Objects.requireNonNull(image.getName()).split("\\.");

        if (split[split.length - 1].equals("jpg")) {

            try (FileInputStream reader = new FileInputStream(image)) {
                bytes = reader.readAllBytes();
            } catch (Exception e) {
                throw new FileReaderException();
            }
        } else
            throw new FileReaderException("wrong file format !");
        Expert expert = service.findById(1L).get();
        service.setProfileImage(bytes,expert);


        assertNotNull(service.findById(1L).get().getImage());
    }

    @Test
    @Order(4)
    void confirmExpert() {

        service.confirmExpert(1L);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(1L).get().getExpertStatus());
    }

    @Test
    @Order(5)
    void addExpertToSubService() {
        SubService subService = SubService.builder().name("subservice").build();
        subServiceService.saveOrUpdate(subService);
        service.addExpertToSubService(11L, subService.getId());

        assertEquals(subService.getName(), service.findById(11L).get().getSubServices().get(0).getName());
    }

    @Test
    @Order(6)
    void deleteExpertOfSubService() {
        Expert expert1 = service.findById(16L).get();
        service.deleteExpertOfSubService(16L, 6L);

        assertThrows(IndexOutOfBoundsException.class,() -> service.findById(11L).get().getSubServices().get(0));


    }
}