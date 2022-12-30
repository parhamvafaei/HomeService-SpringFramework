package com.maktab.entity.dto;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EditSubServiceDTO {
    private Long id;
    private Double price;
    private String description;
}
