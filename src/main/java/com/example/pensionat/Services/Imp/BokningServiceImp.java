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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BokningServiceImp implements BokningService {

    private final BokningRepo br;
    private final RumRepo rumRepo;

    private final KundService kundService;
    private final RumService rumService;

    public String addBokning() {
        return "addBokning";
    }

    //String valueOf och LocalDate parse på nedan metoder, behöver kontroll testas så dom fungerar
    @Override
    public BokningDto BokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).startdatum(String.valueOf(b.getStartdatum())).
                slutdatum(String.valueOf(b.getSlutdatum())).numOfBeds(b.getNumOfBeds()).build();
    }

    @Override
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum) {
        return Bokning.builder().id(b.getId()).startdatum(LocalDate.parse(b.getStartdatum())).
                slutdatum(LocalDate.parse(b.getSlutdatum())).numOfBeds(b.getNumOfBeds())
                .kund(kund).rum(rum).build();
    }

    @Override
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b) {
        return DetailedBokningDto.builder().id(b.getId()).startdatum(String.valueOf(b.getStartdatum())).
                slutdatum(String.valueOf(b.getSlutdatum())).numOfBeds(b.getNumOfBeds())
                .kund(new KundDto(b.getKund().getId(), b.getKund().getNamn(), b.getKund().getTel(), b.getKund().getEmail()))
                .rum(new RumDto(b.getRum().getId(), b.getRum().getRumsnr())).build();
    }
    @Override
    public DetailedBokningDto getBookingDetailsById(Long id) {
        Optional<Bokning> optionalBokning = br.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            return bokningToDetailedBokningDto(bokning);
        } else {
            return null;
        }
    }

    @Override
    public List<DetailedBokningDto> getAllBokningar() {
        return br.findAll().stream().map(bok -> bokningToDetailedBokningDto(bok)).toList();
    }


    /* Kommenterar ut denna för tillfället då jag har samma länkad till thymeleaf
    @Override
    public String addBokning(Bokning b) {
        return null;
    }

     */


    @Override
    public String updateBokning(BokningDto b) {
        return null;
    }

    @Override
    public String deleteBokning(long id) {
        Bokning b = br.findById(id).get();
        if (b != null){
            br.deleteById(id);
            return "Bokningen borttagen";
        }
        return "Bokningen hittas inte";
    }

    @Override
    public String newBokning(String namn, String tel, String email, LocalDate startdatum, LocalDate slutdatum, Long rumId, int numOfBeds) {
        KundDto kundDto = kundService.checkIfKundExistByName(namn, email, tel);
        Kund kund = kundService.kundDtoToKund(kundDto);
        Rum rum = rumService.getRumById(rumId);
        Bokning b = new Bokning(kund, rum, startdatum, slutdatum, numOfBeds);
        br.save(b);
        return "Bokning gjord";
    }
}
