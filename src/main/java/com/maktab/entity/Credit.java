package com.maktab.entity;


import com.maktab.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Credit extends BaseEntity {
    private Double amount;
}
