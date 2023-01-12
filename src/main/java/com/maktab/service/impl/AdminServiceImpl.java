package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.dto.ClientFilterDTO;
import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Role;
import com.maktab.exception.PersonSignInException;
import com.maktab.repository.AdminRepository;
import com.maktab.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, AdminRepository> implements AdminService {

    @PersistenceContext
    private EntityManager em;


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

    @Transactional
    @Override
    public Long createAdmin(String firstName, String lastName, String Email, String password) {

        if (repository.existsByEmail(Email)) {
            throw new PersonSignInException("this admin already exist");
        }

        Admin admin=new Admin(firstName,lastName,Email,password,Role.ROLE_ADMIN);
        admin.setEnabled(true);
        saveOrUpdate(admin);
    return admin.getId();
    }




}
