package com.maktab.entity.dto;

import com.maktab.entity.Address;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private Double price;
    private String description;
    private LocalDateTime time;
    private Address address;
}
