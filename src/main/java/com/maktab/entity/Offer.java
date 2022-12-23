package com.maktab.entity;

import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Offer extends BaseEntity {

    private Double price;
    private Duration durationTime;

    @ManyToOne
    private Expert expert;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Order order;

}
