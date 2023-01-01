package com.maktab.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private AddressDTO address;
}
