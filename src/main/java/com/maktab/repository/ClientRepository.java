package com.maktab.repository;


import com.maktab.entity.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

    boolean existsByEmail(String email);

}
