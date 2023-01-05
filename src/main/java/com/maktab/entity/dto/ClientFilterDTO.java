package com.maktab.entity.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientFilterDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String nationalCode;

}
