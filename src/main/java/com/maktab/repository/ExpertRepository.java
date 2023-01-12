package com.maktab.repository;


import com.maktab.entity.person.Expert;
import com.maktab.entity.person.ExpertStatus;
import com.maktab.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpertRepository  extends JpaRepository<Expert,Long> {

    boolean existsByEmail(String email);

    Optional<Expert> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Expert a " +
            "SET  a.expertStatus = :status WHERE  a.email = :email")
    int confirmedExpert(ExpertStatus status,String email);

    @Modifying
    @Query("select p from Expert p where p.createTime= :signInTime or p.offers.size= :offers or p.ordersDone.size= :orders ")

    List<Expert> filterExpert(LocalDateTime signInTime , Integer offers , Integer orders);
}
