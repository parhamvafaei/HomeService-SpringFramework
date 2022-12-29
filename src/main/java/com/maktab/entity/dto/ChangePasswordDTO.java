package com.maktab.entity.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChangePasswordDTO {
    private Long id ;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8}")
    @NotNull
    private String password;
}
