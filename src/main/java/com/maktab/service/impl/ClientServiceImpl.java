package com.maktab.service.impl;


import com.maktab.base.service.impl.BaseServiceImpl;
import com.maktab.email.registration.token.ConfirmationToken;
import com.maktab.email.registration.token.ConfirmationTokenService;
import com.maktab.entity.Credit;
import com.maktab.entity.dto.ClientFilterDTO;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Role;
import com.maktab.exception.PersonSignInException;
import com.maktab.repository.ClientRepository;
import com.maktab.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ClientServiceImpl extends BaseServiceImpl<Client, ClientRepository> implements ClientService {

    @PersistenceContext
    private EntityManager em;

    private final ConfirmationTokenService confirmationTokenService;


    public ClientServiceImpl(ClientRepository repository, ConfirmationTokenService confirmationTokenService) {
        super(repository);
        this.confirmationTokenService = confirmationTokenService;
    }
    @Transactional
    @Override
    public void changePassword(Long id, String password) {
        Client client = findById(id).orElseThrow(() -> new NullPointerException("cant invoke client"));
        try {
            client.setPassword(password);
        } catch (Exception e) {
            throw new ValidationException("change password failed");
        }
        saveOrUpdate(client);


    }
    @Transactional
    @Override
    public String signIn(String firstName, String lastName, String Email, String password) {

        if (repository.existsByEmail(Email)) {
            throw new PersonSignInException("this client already exist");
        }
        Credit credit = new Credit(0D);
        Client client = new Client(firstName, lastName, Email,password, Role.ROLE_CLIENT, credit);
        saveOrUpdate(client);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                client
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);


        return token;
    }




    @Override
    public List<Client> filterClient(ClientFilterDTO clientDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = query.from(Client.class);

        if (clientDTO.getFirstName() != null) {
            predicateList.add(criteriaBuilder.like(root.get("firstName"), "%" + clientDTO.getFirstName() + "%"));
        }
        if (clientDTO.getLastName() != null) {
            predicateList.add(criteriaBuilder.like(root.get("lastName"), "%" + clientDTO.getLastName() + "%"));
        }
        if (clientDTO.getEmail() != null) {
            predicateList.add(criteriaBuilder.like(root.get("email"), "%" + clientDTO.getEmail() + "%"));
        }

        Predicate[] predicateArray = new Predicate[predicateList.size()];
        predicateList.toArray(predicateArray);
        query.select(root).where(predicateArray);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Client> clientReporter(LocalDateTime signInTime, Integer orderDone) {
        return repository.filterClient(signInTime,orderDone);
    }


    @Override
    public Double showBudget(Long client_id){
        Client client = findById(client_id).orElseThrow(() -> new NullPointerException("cant invoke client"));
        return client.getCredit().getAmount();
    }

}

