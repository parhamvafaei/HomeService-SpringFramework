package com.maktab.entity.person;


import com.maktab.entity.Credit;
import com.maktab.entity.Order;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client extends Person  {

    @OneToOne(cascade = CascadeType.ALL)
    private Credit credit;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Client(String firstName, String lastName,@Email  String email, @NotNull String password, Boolean enabled, Role role, Credit credit) {
        super(firstName, lastName, email, password, enabled, role);
        this.credit = credit;
    }
    @Builder
    public Client(String firstName, String lastName, String email, String password, Role role, Credit credit) {
        super(firstName, lastName, email, password, role);
        this.credit = credit;
    }
}
