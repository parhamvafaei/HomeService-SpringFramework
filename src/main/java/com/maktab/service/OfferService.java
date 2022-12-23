package com.maktab.service;


import com.maktab.base.service.impl.BaseService;
import com.maktab.entity.Offer;
import com.maktab.entity.Order;
import com.maktab.entity.person.Expert;

import java.time.Duration;
import java.util.List;

public interface OfferService extends BaseService<Offer> {

    Long addNewOfferToOrder(Double price, Duration durationTime, Expert expert, Order order);

    List<Offer> offersToOrderByPrice(Long orderId);

    List<Offer> offersToOrderByExpertRate(Long orderId);
}
