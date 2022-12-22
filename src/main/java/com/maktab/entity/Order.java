package com.maktab.entity;

import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Client;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order extends BaseEntity {

    public Order(Double price, String description, LocalDateTime time, Address address) {
        this.price = price;
        this.description = description;
        this.time = time;
        this.address = address;
    }

    private Double price;
    private String description;
    private LocalDateTime time;
    private Boolean isDone;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subService_id")
    private SubService subService;

    @ManyToOne
    private Client client;

    @Enumerated
    private OrderStatus orderStatus;

    @OneToOne
    private Comment comment;

}
