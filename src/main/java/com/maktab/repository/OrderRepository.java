package com.maktab.repository;


import com.maktab.entity.Order;
import com.maktab.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

@Modifying
@Query("select e from Order e where e.client.id= :client_id and e.isDone=true and e.orderStatus= :orderStatus")
    List<Order> clientOrders(Long client_id, String orderStatus);

@Modifying
@Query("select e from Order e where e.expert.id= :expert_id and e.isDone=true and e.orderStatus= :orderStatus")
    List<Order> expertOrders(Long expert_id, String orderStatus);
}
