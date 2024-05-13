package com.example.pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedRumDto {
    private long id;
    protected int rumsnr;
    private boolean dubbelrum;
    private int storlek;
    private int price;
}
