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
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}")
    @NotNull
    private String password;

}
