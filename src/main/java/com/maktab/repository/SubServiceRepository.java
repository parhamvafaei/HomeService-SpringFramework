package com.maktab.repository;




import com.maktab.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Boolean existsByName(String name);




}
