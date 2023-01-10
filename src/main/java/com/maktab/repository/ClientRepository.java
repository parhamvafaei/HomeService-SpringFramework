package com.maktab.repository;


import com.maktab.entity.person.Admin;
import com.maktab.entity.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

    boolean existsByEmail(String email);

    Optional<Client> findByUsername(String username);

}
