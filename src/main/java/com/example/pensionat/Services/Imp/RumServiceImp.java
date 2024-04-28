package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RumServiceImp implements RumService {

    private final RumRepo rr;
    private final BokningRepo bokningRepo;

    private final RumRepo rumRepo;
    private final KundService kundService;



    /*
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

     */

    @Override
    public RumDto rumToRumDto(Rum r) {
        return null;
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
    public Rum DetailedRumDtoToRum(DetailedRumDto r) {
        return null;
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
        return null;
    }

    /*
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

     */

    public Rum getRumById(Long id){
        return rumRepo.findById(id).get();
    }

    @Override
    public String deleteRum(long id) {
        Rum rum = rr.findById(id).get();
        rr.deleteById(id);
        return "Rum nr " + rum.getRumsnr() + " har raderats.";

    }

    @Override
    public String getAllAvailableRooms(String name, String telNr, String email,
                                       String startDate, String endDate, int antalPersoner, Model model) {
        Kund kund = kundService.kundDtoToKund(kundService.checkIfKundExistByName(name, email, telNr));
        //Kolla vilken storlek på rum som kan visas
        boolean needsDouble = antalPersoner > 1;
        int neededSize = antalPersoner - 1;
        String roomType;
        if (needsDouble){
            roomType = "Dubbelrum";
        } else {
            roomType = "Enkelrum";
        }
        //get the dates:
        List<Long> ledigaRumsId = new ArrayList<>();
        List<Rum> sortedRooms = new ArrayList<>();
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            //TODO kontroll för att slut datum är EFTER startdatum
            //TODO Kontroll att start datumet inte has passerat redan

            LocalDate from = LocalDate.parse(startDate);
            LocalDate until = LocalDate.parse(endDate);
            System.out.println("Parsed dates: " + from + " " + until);
            //Hämta ut alla rums-id som inte är bokade under det spannet som angets
            List<Long> notAva = getNonAvailableRoomsId(bokningRepo.findAll(), from, until);
            sortedRooms = rr.findAll().stream().filter(rum -> rum.isDubbelrum() == needsDouble)
                    .filter(rum -> rum.getStorlek() >= neededSize)
                    .filter(rum -> notAva.stream().noneMatch(notAvaRum -> notAvaRum.equals(rum.getId()))).toList();
        } else {
            //TODO felhantering
            System.out.println("Inga eller bara ett datum valdes");
        }
        model.addAttribute("allRooms", sortedRooms);
        model.addAttribute("rubrik", "Lediga rum");
        model.addAttribute("roomType", roomType);
        model.addAttribute("name", kund.getNamn());
        model.addAttribute("telNr", kund.getTel());
        model.addAttribute("email", kund.getEmail());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("antalPersoner", antalPersoner);
        //TODO sortera på bokning måste stämma med rums-id samt datumen. LocalDate parse?
        //TODO Bryta ut till mindre metoder
        return "addBokning";
    }

    List<Long> getNonAvailableRoomsId(List<Bokning> bokningar, LocalDate startDate, LocalDate endDate){
        List<Long> availableRooms = new ArrayList<>();

        for (Bokning bokning:bokningar
        ) {
            if (bokning.getStartdatum().isBefore(endDate) && bokning.getSlutdatum().isAfter(startDate)){
                System.out.println("Added to rooms: " + bokning.getRum().getId());
                availableRooms.add(bokning.getRum().getId());
            } else {
                System.out.println("Broke out of if");
                break;
            }
        }
        System.out.println("Available room id's from getAvailableRoomsId: " + availableRooms);
        return availableRooms;
    }
    boolean roomIdExistInList(Long roomId, List<Long> nonAvailableID){
        return nonAvailableID.stream().anyMatch(id -> id.equals(roomId));
    }
}
