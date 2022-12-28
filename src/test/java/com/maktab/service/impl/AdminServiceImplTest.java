package com.maktab.service.impl;

import com.maktab.entity.person.Admin;
import com.maktab.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;


    @Test
    void changePassword() {
        Admin admin = new Admin();
        admin.setPassword("Pksdjfj2");
        adminService.saveOrUpdate(admin);
        adminService.changePassword(admin.getId(), "FGsrofm3");


        assertEquals("FGsrofm3", adminService.findById(admin.getId()).get().getPassword());
    }

    @Test
    void createAdmin() {
        adminService.createAdmin("padfef","aeff","Parham_fqe2@gamil.com","Prsgmdf3");


    }
}