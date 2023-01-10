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
public class Admin extends Person implements UserDetails {
    @Builder
    public Admin(String firstName, String lastName, String username, @Email String email, @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}") @NotNull String password) {
        super(firstName, lastName, username, email, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
