package com.maktab.repository;


import com.maktab.entity.person.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin,Long> {


    boolean existsByEmail(String email);
    Optional<Admin> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Admin a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
