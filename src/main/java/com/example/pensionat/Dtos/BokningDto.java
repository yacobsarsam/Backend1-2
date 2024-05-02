package com.example.pensionat.Dtos;

import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BokningDto {
    private long id;
    protected String startdatum;
    protected String slutdatum;
    protected int numOfBeds;


}
