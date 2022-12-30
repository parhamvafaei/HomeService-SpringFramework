package com.maktab.entity.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDTO {

    private Float rating;
    private String description;
}
