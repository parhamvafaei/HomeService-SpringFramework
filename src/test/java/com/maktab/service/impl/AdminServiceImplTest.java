package com.maktab.service.impl;

import com.maktab.entity.person.Admin;
import com.maktab.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Test
    @Order(1)
    void createAdmin() {

        Long id = adminService.createAdmin("parham", "vafaei", "parhm@gmail.com", "Parham12");

        assertEquals(1L, id);
    }


    @Test
    @Order(2)
    void changePassword() {
        adminService.changePassword(1L, "Parham12");


        assertEquals("Parham12", adminService.findById(1L).get().getPassword());
    }
}