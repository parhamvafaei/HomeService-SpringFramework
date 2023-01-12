package com.maktab.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maktab.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderFilter {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private OrderStatus orderStatus;
    private String service;
    private String subService;

}
