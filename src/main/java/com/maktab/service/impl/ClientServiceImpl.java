package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import com.maktab.repository.ClientRepository;
import com.maktab.service.ClientService;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class ClientServiceImpl extends BaseServiceImpl<Client, ClientRepository> implements ClientService {


    public ClientServiceImpl(ClientRepository repository) {
        super(repository);
    }

    @Override
    public void changePassword(Long id, String password) {
        Client client = findById(id).orElseThrow(NullPointerException::new);
        try {
            client.setPassword(password);
        } catch (Exception e) {
            throw new ValidationException();
        }
        saveOrUpdate(client);
    }

}
