package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Models.Bokning;

import java.util.List;

public interface BokningService {

    public BokningDto BokningToBokningDto(Bokning bokning);
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b);
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);
    public List<DetailedBokningDto> getAllBokningar();
    public String addBokning(Bokning b);
    public String updateBokning(BokningDto b);
    public String deleteBokning(long id);
}
