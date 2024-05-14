package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KundServiceImp implements KundService {

    private final KundRepo kr;
    @Override
    public List<KundDto> getAllKunder() {
        return kr.findAll().stream().map(k -> kundToKundDto(k)).toList();
    }

    @Override
    public List<DetailedKundDto> getAllKunder2() {
        return kr.findAll().stream().map(k -> kundToDetailedKundDto(k)).toList();
    }
    @Override
    public String addKund(Kund k, Model model) {
        if (isCustomerFieldsFilledAndCorrect(k.getNamn(), k.getTel(), k.getEmail())){
            kr.save(k);
            return "updateKundDone";
        } else {
            model.addAttribute("felmeddelande", "Fel i kundfälten, kontrollera och försök igen");
            model.addAttribute("updatekund", k);
            return "updatekund";
        }
    }

    @Override
    public Kund updateKund(long id) {
        Kund k =kr.findById(id).get();
                return k;
    }

    @Override
    public boolean checkIfKundExistByEmailUtanAttSkapa(String namn, String email, String tel) {
        KundDto kundDto = getAllKunder().stream().filter(kund -> Objects.equals(kund.getEmail(), email)).findFirst().orElse(null);
        if(kundDto == null){

            return false;
        }
        else{

            return true;
        }
    }
    @Override
    public KundDto checkIfKundExistByEmail(String name, String email, String telefon){
        KundDto kundDto = getAllKunder().stream().filter(kund -> Objects.equals(kund.getEmail(), email)).findFirst().orElse(null);
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

    @Override
    public DetailedKundDto kundToDetailedKundDto(Kund k) {
        return DetailedKundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).email(k.getEmail())
                .bokningDtos(k.getBokning().stream().map(b -> bokningToBokningDto(b)).toList()).build();
    }

    //TODO flyttas till bokningsServicen
    public BokningDto bokningToBokningDto(Bokning b) {
        return BokningDto.builder().id(b.getId()).startdatum(String.valueOf(b.getStartdatum())).
                slutdatum(String.valueOf(b.getSlutdatum())).numOfBeds(b.getNumOfBeds()).build();
    }
    @Override
    public boolean checkIfKundHasBokningar(Long kundId) {
        Kund kund = kr.findById(kundId).orElse(null);
        return kund!= null && !kund.getBokning().isEmpty();
    }

    @Override
    public String deleteKund(long id) {
        // Kontrollera om kunden har bokningar kopplade till sig
        boolean hasBokningar = checkIfKundHasBokningar(id);
        if (hasBokningar) {
            return "Kunden har bokningar kopplade till sig och kan inte tas bort.";
        } else {
            try {
                kr.deleteById(id);
                return "Kunden har tagits bort.";
            } catch (Exception e) {
                return "Ett fel uppstod vid borttagning av kunden.";
            }
        }
    }
    @Override
    public Kund getKundById(Long id) {
        return kr.findById(id).orElse(null);
    }
    public String getAllAvailableKundInfo(String name, String telNr, String email, Model model) {
        String felmeddelande;
        if (!isCustomerFieldsFilledAndCorrect(name, telNr, email)){
            felmeddelande = "Fel i kund-fälten, kontrollera och försök igen";
            model.addAttribute("felmeddelande", felmeddelande);
            return addModelsAndReturn(name, telNr, email, model);
        }
        else
            return "visakunder";
    }
    public boolean isCustomerFieldsFilledAndCorrect(String name, String telnr, String email){
        if (name.trim().isEmpty()){
            return false;
        } else if (telnr.trim().length() < 10 && !telnr.trim().matches("[0-9+-}+]")) {
            return false;
        } else return !email.trim().isEmpty();
    }
    String addModelsAndReturn(String name, String telnr, String email, Model model){
        model.addAttribute("name", name);
        model.addAttribute("telNr", telnr);
        model.addAttribute("email", email);
        return "visakunder";
    }
}
