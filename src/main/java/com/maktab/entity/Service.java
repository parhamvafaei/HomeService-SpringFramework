package com.maktab.entity;

import com.maktab.base.entity.BaseEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Service extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SubService> subServices = new ArrayList<>();

}
