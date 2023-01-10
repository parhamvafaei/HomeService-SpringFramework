package com.maktab.service;


import com.maktab.base.service.BaseService;
import com.maktab.entity.Address;
import com.maktab.entity.Comment;
import com.maktab.entity.Order;
import com.maktab.entity.SubService;
import com.maktab.entity.dto.AddressDTO;
import com.maktab.entity.person.Expert;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends BaseService<Order> {
    Long addOrder(Double price, String description, LocalDateTime time, AddressDTO address, SubService subService);



    void selectExpertToOrder(Long offerId, Long orderId);

    void changeOrderStatusToStarted(Long id);

    void changeOrderStatusToDone(Long id, Duration time);

    List<Order> showRelatedOrdersBySubService(Long expertId);

    void setComment(Comment comment, Long order_id);

    Float showCommentRatingToExpert(Long id, Long expert_id);

    void expertAccountStatus(Long orderId, Long expert_id);

    Expert findExpert(Long order_id);


    void payFromCredit(Long order_id, Long client_id);
    void payFromCard(Long order_id);

    void setExpertScore(Long order_id, Float rating);

}
