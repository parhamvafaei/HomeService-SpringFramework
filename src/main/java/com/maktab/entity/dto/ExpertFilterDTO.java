package com.maktab.entity.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertFilterDTO {

    private String firstname;
    private String lastname;
    private String email;
    private Double score;


}
