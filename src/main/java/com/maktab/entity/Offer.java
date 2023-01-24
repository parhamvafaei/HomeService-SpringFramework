package com.maktab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Offer extends BaseEntity {

    private Double price;
    private Duration durationTime;

    private boolean isSet;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private Expert expert;

    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(price, offer.price) && Objects.equals(durationTime, offer.durationTime) && Objects.equals(expert, offer.expert) && Objects.equals(order, offer.order);
    }


}
