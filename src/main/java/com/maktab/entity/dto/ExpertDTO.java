package com.maktab.entity.dto;


import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpertDTO {

    private String firstName;
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

}
