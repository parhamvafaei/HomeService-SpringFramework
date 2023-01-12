package com.maktab.repository;


import com.maktab.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


public interface PersonRepository<E extends Person> extends JpaRepository<E,Long> {


    @Query("from Person p where p.email =:email")
    Optional<E> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("update Person p set p.enabled=true where p.email=:email")
    int enableAppUser(String email);

}
