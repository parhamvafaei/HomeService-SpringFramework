package com.maktab.entity.person;

import com.maktab.entity.Offer;
import com.maktab.entity.SubService;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expert extends Person {

    private Float rating = 0F;
    private Double totalMoney;

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @Lob
    @Size(max = 300_000)
    private byte[] image;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SubService> subServices = new ArrayList<>();

    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Offer> offers = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expert expert = (Expert) o;
        return Objects.equals(rating, expert.rating) && getId().equals(expert.getId()) && expertStatus == expert.expertStatus && Arrays.equals(image, expert.image);
    }
    @Builder
    public Expert(String firstName, String lastName, @Email String email, @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}") @NotNull String password, Boolean enabled, Role role, Float rating, Double totalMoney, ExpertStatus expertStatus, @Size(max = 300_000) byte[] image) {
        super(firstName, lastName, email, password, enabled, role);
        this.rating = rating;
        this.totalMoney = totalMoney;
        this.expertStatus = expertStatus;
        this.image = image;
    }
}
