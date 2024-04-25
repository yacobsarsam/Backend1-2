package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KundServiceImp implements KundService {

    private final KundRepo kr;
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

    @Override
    public KundDto checkIfKundExistByName(String name, String email, String telefon){
        KundDto kundDto = getAllKunder().stream().filter(kund -> Objects.equals(kund.getNamn(), name)).findFirst().orElse(null);
        if(kundDto == null){
            Kund k = new Kund(name, telefon, email);
            kr.save(k);
            return kundToKundDto(k);
        }
        else{
            return kundDto;
        }
    }

    @Override
    public Kund kundDtoToKund(KundDto k) {
        return Kund.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).email(k.getEmail()).build();
    }
    @Override
    public KundDto kundToKundDto(Kund k) {
        return KundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).email(k.getEmail()).build();
    }

    /*
    private final KundRepo kr;
        private final BokningService bokningService;



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
