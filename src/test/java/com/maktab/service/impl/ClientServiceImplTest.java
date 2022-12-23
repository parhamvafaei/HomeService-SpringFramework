package com.maktab.service.impl;

import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    private ClientService service;

    @Test
    void changePassword() {

        Client client=new Client();
        client.setPassword("Pksdjfj2");
        service.saveOrUpdate(client);
        service.changePassword(client.getId(), "FGsrofm3");

        assertEquals("FGsrofm3", service.findById(client.getId()).get().getPassword());
    }
    }
