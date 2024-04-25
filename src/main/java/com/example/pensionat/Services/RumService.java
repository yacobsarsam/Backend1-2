package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;

import java.util.List;

public interface RumService {

    public RumDto rumToRumDto(Rum r);
    public Rum rumDtoToRum(RumDto r);
    public DetailedRumDto rumToDetailedRumDto(Rum r);
    public List<DetailedRumDto> getAllRum();
    public String addRum(Rum r);
    public String updateRum(RumDto r);
    public String deleteRum(long id);
}
