package com.maktab.repository;


import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {


}
