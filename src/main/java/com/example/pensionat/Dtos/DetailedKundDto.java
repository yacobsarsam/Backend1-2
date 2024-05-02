package com.example.pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedKundDto {
    private long id;
    private String namn;
    private String tel;
    private String email;
    List<BokningDto> bokningDtos;
}
