package com.maktab.entity.dto;

import lombok.*;
import org.hibernate.internal.build.AllowSysOut;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AdminDTO {

    private Long id ;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}")
    @NotNull
    private String password;
}
