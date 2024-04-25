package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RumServiceImp implements RumService {

    private final RumRepo rr;

    @Override
    public RumDto rumToRumDto(Rum r) {
        return RumDto.builder().id(r.getId()).rumsnr(r.getRumsnr()).dubbelrum(r.isDubbelrum()).storlek(r.getStorlek()).build();
    }

    @Override
    public Rum rumDtoToRum(RumDto r) {
        return Rum.builder().id(r.getId()).rumsnr(r.getRumsnr()).dubbelrum(r.isDubbelrum()).storlek(r.getStorlek()).build();
    }

    @Override
    public DetailedRumDto rumToDetailedRumDto(Rum r) {
        return DetailedRumDto.builder().id(r.getId()).rumsnr(r.getRumsnr()).dubbelrum(r.isDubbelrum()).storlek(r.getStorlek()).build();
    }

    @Override
    public List<DetailedRumDto> getAllRum() {
        return rr.findAll().stream().map(rum -> rumToDetailedRumDto(rum)).toList();
    }

    @Override
    public String addRum(Rum r) {
        rr.save(r);
        return "Rum nr " + r.getRumsnr() + " har sparats.";
    }

    @Override
    public String updateRum(RumDto r) {
        Rum rum = rr.findById(r.getId()).get();
        if (r.getRumsnr() != 0)
            rum.setRumsnr(r.getRumsnr());
        //Vet inte hur man ska testa denna, får återkomma senare
        rum.setDubbelrum(r.isDubbelrum());
        if (r.getStorlek() != 0)
            rum.setStorlek(r.getStorlek());
        rr.save(rum);
        return "Rummet har uppdaterats.";
    }

    @Override
    public String deleteRum(long id) {
        Rum rum = rr.findById(id).get();
        rr.deleteById(id);
        return "Rum nr " + rum.getRumsnr() + " har raderats.";

    }
}
