package com.maktab.entity.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientFilterDTO {

    private String firstName;
    private String lastName;
    private String email;


}
