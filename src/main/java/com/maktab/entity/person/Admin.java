package com.maktab.entity.person;


import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Admin extends Person {
    @Builder
    public Admin(String firstName, String lastName, String Email, String password) {

        super(firstName, lastName, Email, password);
    }
}
