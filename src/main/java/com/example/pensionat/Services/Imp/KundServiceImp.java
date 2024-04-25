package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KundServiceImp implements KundService {
    @Override
    public List<KundDto> getAllKunder() {
        return null;
    }

    @Override
    public String addKund(Kund k) {
        return null;
    }

    @Override
    public String updateKund(KundDto k) {
        return null;
    }

    @Override
    public String deleteKund(long id) {
        return null;
    }

    /*
    private final KundRepo kr;
        private final BokningService bokningService;

    @Override
    public KundDto kundToKundDto(Kund k) {
        return KundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).email(k.getEmail()).build();
    }

    @Override
    public Kund kundDtoToKund(KundDto k) {
        return Kund.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).build();
    }

    @Override
    public List<KundDto> getAllKunder() {
        return kr.findAll().stream().map(k -> kundToKundDto(k)).toList();
    }

    @Override
    public String addKund(Kund k) {
        kr.save(k);
        return "Kunden " + k.getNamn() + " har sparats.";
    }

    @Override
    public String updateKund(KundDto k) {
        Kund kund = kr.findById(k.getId()).get();
        if (k.getNamn() != null && !k.getNamn().isEmpty())
            kund.setNamn(k.getNamn());
        if (k.getTel() != null && !k.getTel().isEmpty())
            kund.setTel(k.getTel());
        if (k.getEmail() != null && !k.getEmail().isEmpty())
            kund.setEmail(k.getEmail());
        kr.save(kund);
        return "Kunden har uppdaterats.";
    }

    @Override
    public String deleteKund(long id) {
        Kund kund = kr.findById(id).get();
        kr.deleteById(id);
        return "Kunden " + kund.getNamn() + " har raderats.";
    }

    @Override
    public List<DetailedKundDto> getAllCustomers() {
        return kr.findAll().stream().map(k -> kundToDetailedKundDto(k)).toList();
    }

    @Override
    public DetailedKundDto kundToDetailedKundDto(Kund k) {
        return DetailedKundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).
                email(k.getEmail()).bokningDtos(k.getBokning().stream().
                        map(bokning -> bokningService.BokningToBokningDto(bokning)).toList()).build();
    }

     */
}
