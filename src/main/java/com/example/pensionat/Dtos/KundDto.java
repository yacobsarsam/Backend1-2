package com.example.pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KundDto {
    private long id;
    private String namn;
    private String tel;
    private String email;
}
