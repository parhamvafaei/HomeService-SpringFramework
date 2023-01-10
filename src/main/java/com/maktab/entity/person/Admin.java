package com.maktab.entity.person;


import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Admin extends Person {
    @Builder
    public Admin(String firstName, String lastName, String username, @Email String email, @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}") @NotNull String password) {
        super(firstName, lastName, username, email, password);
    }

}
