package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KundServiceImp implements KundService {

    final private KundRepo kr;

    @Override
    public KundDto kundToKundDto(Kund k) {
        return KundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).build();
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
    public String addKund(KundDto k) {
        kr.save(kundDtoToKund(k));
        return "Kunden " + k.getNamn() + " har sparats.";
    }

    @Override
    public String updateKund(KundDto k) {
        Kund kund = kr.findById(k.getId()).get();
        if (k.getNamn() != null)
            kund.setNamn(k.getNamn());
        if (k.getTel() != null)
            kund.setTel(k.getTel());
        if (k.getEmail() != null)
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
}
