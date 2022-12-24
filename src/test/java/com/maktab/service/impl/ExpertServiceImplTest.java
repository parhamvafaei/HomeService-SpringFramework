package com.maktab.service.impl;


import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.service.ExpertService;
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

    @Test
    void changePassword() {
        Expert expert = new Expert();
        expert.setPassword("Pksdjfj2");
        service.saveOrUpdate(expert);
        service.changePassword(expert.getId(), "FGsrofm3");


        assertEquals("FGsrofm3", service.findById(expert.getId()).get().getPassword());
    }

//#
    @Test
    void setProfileImage() throws IOException {
        Expert expert = new Expert();
        service.saveOrUpdate(expert);
        File file = new File("IMG_20220225_174859_946.jpg");

        service.setProfileImage(file, expert.getId());

        FileInputStream reader = new FileInputStream(file);
        byte[] bytes = reader.readAllBytes();

        assertArrayEquals(service.findById(expert.getId()).get().getImage(), bytes);
    }

    @Test
    void confirmExpert() {
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        service.saveOrUpdate(expert);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(expert.getId()).get().getExpertStatus());
    }

}