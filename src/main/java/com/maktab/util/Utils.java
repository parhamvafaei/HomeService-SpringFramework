package com.maktab.util;

import com.maktab.entity.Order;
import com.maktab.entity.OrderStatus;
import com.maktab.entity.Service;
import com.maktab.entity.SubService;

import com.maktab.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//how to inject static field
//why use no args constructor
public class Utils {

    @Autowired
    private OrderService orderService;

    public static boolean existsSubServiceInService(SubService subService, Service service) {

        return subService.getService().getName().equals(service.getName());
    }

    public static List<Order> findOrdersToOffer() {
        return new Utils().orderService.findAll().stream()
                .filter(order -> order
                        .getOrderStatus()
                        .equals(OrderStatus.WAITING_FOR_EXPERT))
                        .toList();

    }
}
