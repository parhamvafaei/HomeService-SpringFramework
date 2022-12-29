package com.maktab.entity.dto;

import lombok.*;

import java.time.Duration;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfferDTO {
    private Double price;
    private Duration durationTime;
}
