package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.person.Admin;
import com.maktab.repository.AdminRepository;
import com.maktab.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;


//what if you don't use repository as field

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminRepository> implements AdminService {


    public AdminServiceImpl(AdminRepository repository) {
        super(repository);
    }

    @Transactional
    @Override
    public void changePassword(Long id, String password) {
        Admin admin = findById(id).orElseThrow(NullPointerException::new);
        try {
            admin.setPassword(password);

        } catch (Exception e) {
            throw new ValidationException();
        }
        saveOrUpdate(admin);
    }
}
