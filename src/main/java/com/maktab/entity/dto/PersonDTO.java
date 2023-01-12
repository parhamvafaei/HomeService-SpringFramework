package com.maktab.entity.dto;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonDTO {
    private LocalDateTime signInTime;
    private Integer ordersSet;
    private Integer ordersDone;

}
