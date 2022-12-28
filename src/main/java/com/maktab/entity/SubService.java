package com.maktab.entity;


import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SubService extends BaseEntity {
    private String name;
    private Double price;
    private String description;


    @ManyToOne(cascade = CascadeType.ALL)
    private Service service;

    @ManyToMany(mappedBy = "subServices", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Expert> experts =new ArrayList<>();

@OneToMany(mappedBy = "subService")
@ToString.Exclude
private List<Order> orders;
}
