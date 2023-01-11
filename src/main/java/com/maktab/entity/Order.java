package com.maktab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Client;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
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
    private Boolean isDone = false;

    private Duration actualDurationTime;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @JsonIgnore
    @ManyToOne
    private SubService subService;
    @JsonIgnore
    @ManyToOne
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Expert expert;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Comment comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(price, order.price) && Objects.equals(description, order.description) && Objects.equals(time, order.time) && Objects.equals(isDone, order.isDone) && Objects.equals(actualDurationTime, order.actualDurationTime);
    }


}
