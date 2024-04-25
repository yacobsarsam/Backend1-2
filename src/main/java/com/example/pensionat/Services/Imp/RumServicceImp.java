package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RumServicceImp implements RumService {
    @Override
    public RumDto rumToRumDto(Rum r) {
        return RumDto.builder().;
    }

    @Override
    public Rum rumDtoToRum(RumDto r) {
        return null;
    }

    @Override
    public DetailedRumDto rumToDetailedRumDto(Rum r) {
        return null;
    }

    @Override
    public List<DetailedRumDto> getAllRum() {
        return null;
    }

    @Override
    public String addRum(Rum r) {
        return null;
    }

    @Override
    public String updateRum(RumDto r) {
        return null;
    }

    @Override
    public String deleteRum(long id) {
        return null;
    }
}
