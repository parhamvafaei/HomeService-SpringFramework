package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.dto.ClientFilterDTO;
import com.maktab.entity.person.Client;

import java.util.List;

public interface ClientService extends BaseService<Client> {

    void changePassword(Long id, String password);

    Long signIn(String firstName, String lastName, String Email, String password);

    List<Client> filterClient(ClientFilterDTO clientDTO);
}
