package com.maktab.entity.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class SubServiceDTO {

    private String name;
    private Double price;
    private String description;
}
