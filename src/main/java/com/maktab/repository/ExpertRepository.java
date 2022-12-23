package com.maktab.repository;


import com.maktab.entity.person.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertRepository  extends JpaRepository<Expert,Long> {


}
