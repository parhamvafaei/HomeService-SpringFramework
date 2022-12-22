package com.maktab.entity.person;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Admin extends Person{
    public Admin(String firstName, String firstLast, @javax.validation.constraints.Email String Email, @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}") String password) {
        super(firstName, firstLast, Email, password);
    }
}
