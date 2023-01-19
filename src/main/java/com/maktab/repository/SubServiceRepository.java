package com.maktab.repository;




import com.maktab.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    Boolean existsByName(String name);

Optional<SubService> findByName(String name);


}
