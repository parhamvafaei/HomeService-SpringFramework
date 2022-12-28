package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.Address;
import com.maktab.entity.Order;
import com.maktab.entity.SubService;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends BaseService<Order> {
    Long addOrder(Double price, String description, LocalDateTime time, Address address, SubService subService);

    void chooseExpert(Long id);

    void changeOrderStatusToStarted(Long id);

    void changeOrderStatusToDone(Long id);

    List<Order> showRelatedOrdersBySubService(Long expertId);

    List<Order> findOrdersToOffer();
}
