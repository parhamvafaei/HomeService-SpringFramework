package com.maktab.entity;


import com.maktab.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends BaseEntity {

    private String address;
    private Long phoneNumber;


}
