package com.maktab.entity.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressDTO {

    private String address;
    private Long phoneNumber;
}
