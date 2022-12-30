package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.Address;
import com.maktab.entity.Comment;
import com.maktab.entity.Order;
import com.maktab.entity.SubService;
import com.maktab.entity.person.Expert;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends BaseService<Order> {
    Long addOrder(Double price, String description, LocalDateTime time, Address address, SubService subService);



    void selectExpertToOrder(Long offerId, Long orderId);

    void changeOrderStatusToStarted(Long id);

    void changeOrderStatusToDone(Long id);

    List<Order> showRelatedOrdersBySubService(Long expertId);

    void setComment(Comment comment, Long order_id);

    Float showCommentRatingToOrder(Long id, Long expert_id);
}
