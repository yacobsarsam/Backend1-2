package com.example.pensionat.Pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RumDto {
    private long id;
    private boolean dubbelrum;
    private int storlek;
    private int numOfBeds;
}
