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


public class Utils {


    public static boolean existsSubServiceInService(SubService subService, Service service) {
        boolean equals;
        try {
          equals = subService.getService().getName().equals(service.getName());
     }catch (NullPointerException e ){
         return false;
     }
        return equals;
    }


}
