package com.maktab.service;


//get permission to expert just when is confirmed


import com.maktab.base.service.BaseService;
import com.maktab.entity.person.Admin;

import java.util.Optional;

public interface AdminService extends BaseService<Admin> {

    void changePassword(Long id, String password);

    Long createAdmin(String firstName, String lastName, String Email, String password);
}
