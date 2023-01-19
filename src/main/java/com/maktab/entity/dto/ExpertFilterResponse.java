package com.maktab.entity.dto;


import com.maktab.entity.person.ExpertStatus;
import com.maktab.entity.person.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertFilterResponse {

    private String firstName;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;

    private Float rating = 0F;
    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @Enumerated(EnumType.STRING)
    private Role role;
}
