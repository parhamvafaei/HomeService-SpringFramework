package com.maktab.entity.person;


import com.maktab.entity.Credit;
import com.maktab.entity.Order;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client extends Person {

    @OneToOne
private Credit credit;

    @OneToMany(mappedBy = "client" ,cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders;
}
