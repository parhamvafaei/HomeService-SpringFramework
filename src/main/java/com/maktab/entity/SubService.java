package com.maktab.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.base.entity.BaseEntity;
import com.maktab.entity.person.Expert;
import lombok.*;

import javax.persistence.*;
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
public class SubService extends BaseEntity {
    private String name;
    private Double price;
    private String description;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Service service;

    @JsonIgnore
    @ManyToMany(mappedBy = "subServices", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Expert> experts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "subService")
    @ToString.Exclude
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubService that = (SubService) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(description, that.description);
    }


}
