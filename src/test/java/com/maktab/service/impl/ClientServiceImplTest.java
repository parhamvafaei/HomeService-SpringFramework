package com.maktab.service.impl;

import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.service.ClientService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceImplTest {

    @Autowired
    private ClientService service;

    @Test
    @Order(1)
    void signIn() {
        Long id = service.signIn("parham", "vafaei", "parhm@gmail.com", "Parham12");
        assertEquals(1L, id);

    }

    @Test
    @Order(2)
    void changePassword() {

        service.changePassword(1L, "Ppppppp2");

        assertEquals("Ppppppp2", service.findById(1L).get().getPassword());
    }
}
