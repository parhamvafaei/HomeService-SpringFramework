package com.maktab.repository;


import com.maktab.entity.person.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin,Long> {


    boolean existsByEmail(String email);
}
