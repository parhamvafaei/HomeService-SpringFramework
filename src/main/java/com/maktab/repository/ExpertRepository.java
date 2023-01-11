package com.maktab.repository;


import com.maktab.entity.person.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ExpertRepository  extends JpaRepository<Expert,Long> {

    boolean existsByEmail(String email);

    Optional<Expert> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Expert a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
