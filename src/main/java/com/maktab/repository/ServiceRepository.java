package com.maktab.repository;


import com.maktab.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Boolean existsByName(String name);


}
