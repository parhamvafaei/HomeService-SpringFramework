package com.maktab.entity.dto;


import com.maktab.entity.person.ExpertStatus;
import com.maktab.entity.person.Role;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertFilterResponse {
    private Float rating = 0F;
    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @Enumerated(EnumType.STRING)
    private Role role;
}
