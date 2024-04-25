package com.example.pensionat.Services.Imp;


import com.example.pensionat.Dtos.*;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BokningServiceImp implements BokningService {

    private final BokningRepo br;

    private final KundService kundService;
    private final RumService rumService;

    @Override
    public BokningDto BokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).startdatum(b.getStartdatum()).
                slutdatum(b.getSlutdatum()).numOfBeds(b.getNumOfBeds()).build();
    }

    @Override
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum) {
        return Bokning.builder().id(b.getId()).startdatum(b.getStartdatum()).
                slutdatum(b.getSlutdatum()).numOfBeds(b.getNumOfBeds())
                .kund(kund).rum(rum).build();
    }

    @Override
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b) {
        return DetailedBokningDto.builder().id(b.getId()).startdatum(b.getStartdatum()).
                slutdatum(b.getSlutdatum()).numOfBeds(b.getNumOfBeds())
                .kund(new KundDto(b.getKund().getId(), b.getKund().getNamn()))
                .rum(new RumDto(b.getRum().getId(), b.getRum().getRumsnr())).build();
    }

    @Override
    public List<DetailedBokningDto> getAllBokningar() {
        return br.findAll().stream().map(bok -> bokningToDetailedBokningDto(bok)).toList();
    }

    @Override
    public String addBokning(Bokning b) {
        return null;
    }

    @Override
    public String updateBokning(BokningDto b) {
        return null;
    }

    @Override
    public String deleteBokning(long id) {
        return null;
    }
}
