package com.maktab.repository;


import com.maktab.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {


    List<Offer> findByOrderId(Long id);
}
