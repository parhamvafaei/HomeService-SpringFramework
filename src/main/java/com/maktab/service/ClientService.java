package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.person.Client;

public interface ClientService extends BaseService<Client> {

    void changePassword(Long id, String password);

}
