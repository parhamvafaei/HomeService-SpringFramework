package com.maktab.entity.person;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Admin extends Person{
    @Builder
    public Admin(String firstName, String lastName, @Email String email, @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}") @NotNull String password, Boolean enabled, Role role) {
        super(firstName, lastName, email, password, enabled, role);
    }

    public Admin(String firstName, String lastName, String email, String password, Role role) {
        super(firstName, lastName, email, password, role);
    }
}
