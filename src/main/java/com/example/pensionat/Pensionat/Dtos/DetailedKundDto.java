package com.example.pensionat.Pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedKundDto {
    private long id;
    private String namn;
    private String tel;
}
