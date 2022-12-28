package com.maktab.entity;

import com.maktab.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment extends BaseEntity {

    private Float rating;
    private String description;

    @OneToOne(mappedBy = "comment")
    private Order order;
}
