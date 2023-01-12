package com.maktab.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonDTO {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signInTime;
    private Integer ordersSet;
    private Integer ordersDone;

}
