package com.maktab.entity;

import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Duration;

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

    @ManyToOne(cascade = CascadeType.MERGE)
    private Expert expert;

    @ManyToOne
    private Order order;

}
