package com.maktab.entity.person;


import com.maktab.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Person extends BaseEntity {
    private String firstName;
    private String firstLast;

    @Email  @Column(unique = true)
    private String Email;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}")
    @NotNull
    private String password;

}
