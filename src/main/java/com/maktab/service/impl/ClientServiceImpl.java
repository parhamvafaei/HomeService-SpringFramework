package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.entity.Credit;
import com.maktab.entity.dto.ClientFilterDTO;
import com.maktab.entity.person.Client;
import com.maktab.exception.PersonSignInException;
import com.maktab.repository.ClientRepository;
import com.maktab.service.ClientService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClientServiceImpl extends BaseServiceImpl<Client, ClientRepository> implements ClientService {

    @PersistenceContext
    private EntityManager em;

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

    @Override
    public Long signIn(String firstName, String lastName, String Email, String password) {

        if (repository.existsByEmail(Email)) {
            throw new PersonSignInException("this client already exist");
        }
        Credit credit = new Credit(0D);
        Client client = new Client(firstName, lastName, Email, password, credit);
        saveOrUpdate(client);

        return client.getId();
    }



}

