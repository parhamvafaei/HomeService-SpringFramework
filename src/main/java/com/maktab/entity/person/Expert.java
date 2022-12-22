package com.maktab.entity.person;

import com.maktab.entity.Offer;
import com.maktab.entity.SubService;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expert extends Person {

    private Float rating = 0F;

    @Enumerated
    private ExpertStatus expertStatus;

    @Lob
    @Size(max = 300_000)
    private byte[] image;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "expert_subService"
            , joinColumns = @JoinColumn(name = "expert_id")
            , inverseJoinColumns = @JoinColumn(name = "subService_id"))
    @ToString.Exclude
    private List<SubService> subServices;

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Offer> offers;
}
