package com.maktab.entity.dto;

import com.maktab.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderFilter {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private OrderStatus orderStatus;
    private String service;
    private String subService;

}
