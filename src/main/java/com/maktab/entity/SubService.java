package com.maktab.entity;


import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubService extends BaseEntity {
    private String name;
    private Double price;
    private String description;


    @ManyToOne
    private Service service;
    //why use many to many
    @ManyToMany(mappedBy = "subServices" )
    @ToString.Exclude
    private List<Expert> experts;
}
