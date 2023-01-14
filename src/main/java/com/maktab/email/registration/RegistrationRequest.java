package com.maktab.email.registration;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}")
    @NotNull
    private String password;
}


