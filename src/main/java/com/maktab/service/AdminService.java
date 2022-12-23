package com.maktab.service;


//get permission to expert just when is confirmed


import com.maktab.base.service.impl.BaseService;
import com.maktab.entity.person.Admin;

public interface AdminService extends BaseService<Admin> {

    void changePassword(Long id, String password);


}
