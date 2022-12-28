package com.maktab.entity.person;


import com.maktab.entity.Credit;
import com.maktab.entity.Order;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client extends Person {

    @OneToOne(cascade = CascadeType.ALL)
    private Credit credit;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Client(String firstName, String lastName,String Email ,String password, Credit credit) {

        super(firstName, lastName, Email, password);
        this.credit = credit;
    }
}
