package com.maktab.repository;


import com.maktab.entity.person.Client;
import com.maktab.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Client a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Modifying
    @Query("select p from Client p where p.createTime= :signInTime or p.orders.size= :ordersSet ")
    List<Client> filterClient(LocalDateTime signInTime , Integer ordersSet );
}
