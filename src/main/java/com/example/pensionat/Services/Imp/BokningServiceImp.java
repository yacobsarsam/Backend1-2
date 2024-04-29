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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public Bokning getBookingDetailsById(Long id) {
        Optional<Bokning> optionalBokning = br.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            return bokning;
        } else {
            return null;
        }
    }

    @Override
    public List<DetailedBokningDto> getAllBokningar() {
        return br.findAll().stream().map(bok -> bokningToDetailedBokningDto(bok)).toList();
    }

    @Override
    public List<Bokning> getAllBokningar2() {
        return br.findAll();
    }

    @Override
    public List<BokningDto> getAllBokningarbyId(Long id) {
        return br.findAll().stream().filter(b -> b.getKund().getId() == id).map(bok -> BokningToBokningDto(bok)).toList();
    }
    /* Kommenterar ut denna för tillfället då jag har samma länkad till thymeleaf
    @Override
    public String addBokning(Bokning b) {
        return null;
    }

     */


//    @Override
//    public String updateBokning(DetailedBokningDto b) {
//        Model model = null;
//        rumService.getAllAvailableRooms(b.getKund().getNamn(), b.getKund().getTel(), b.getKund().getEmail(),
//                b.getStartdatum(), b.getSlutdatum(), String.valueOf(b.getNumOfBeds()), model);
//        return "addBokning";
//    }

    @Override
    public Bokning updateBokning(Long bokId, LocalDate startDate, LocalDate endDate, int numOfBeds, Long rumId) {
        System.out.println("bokningsId är: " + bokId);
        Bokning b = getBookingDetailsById(bokId);
        System.out.println("rumsId är: " + rumId);
        Rum r = rumService.getRumById(rumId);
        b.setRum(r);
        b.setStartdatum(startDate);
        b.setSlutdatum(endDate);
        b.setNumOfBeds(numOfBeds);
        return b;
    }

    @Override
    public String deleteBokning(long id) {
        Optional<Bokning> optionalBokning = br.findById(id);
        if (optionalBokning.isPresent()) {
            Bokning bokning = optionalBokning.get();
            br.delete(bokning);
            return "Bokningen borttagen";
        } else {
            return "Bokningen hittas inte";
        }
    }
    @Override
    public Bokning newBokning(String namn, String tel, String email, LocalDate startdatum, LocalDate slutdatum, Long rumId, int numOfBeds) {
        KundDto kundDto = kundService.checkIfKundExistByName(namn, email, tel);
        Kund kund = kundService.kundDtoToKund(kundDto);
        Rum rum = rumService.getRumById(rumId);
        Bokning b = new Bokning(kund, rum, startdatum, slutdatum, numOfBeds);
        br.save(b);
        return b;
    }

    @Override
    public String getAllAvailableRooms(Long bokId, Long rumId, String startDate, String endDate,
                                       String antalPersoner, Model model) {
        Bokning booking = getBookingDetailsById(bokId);
        int antalPersonerInt = Integer.parseInt(antalPersoner);
        //Kolla vilken storlek på rum som kan visas
        boolean needsDouble = antalPersonerInt > 1;
        int neededSize = antalPersonerInt - 1;
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
            List<Long> notAva = rumService.getNonAvailableRoomsId(getAllBokningar2(), from, until);
            sortedRooms = rumService.getAllRum2().stream().filter(rum -> rum.isDubbelrum() == needsDouble)
                    .filter(rum -> rum.getStorlek() >= neededSize)
                    .filter(rum -> notAva.stream().noneMatch(notAvaRum -> notAvaRum.equals(rum.getId()))).toList();
        } else {
            //TODO felhantering
            System.out.println("Inga eller bara ett datum valdes");
        }
        model.addAttribute("allRooms", sortedRooms);
        model.addAttribute("booking", booking);
        model.addAttribute("rubrik", "Lediga rum");
        model.addAttribute("roomType", roomType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("numOfBeds", antalPersonerInt);
        //TODO sortera på bokning måste stämma med rums-id samt datumen. LocalDate parse?
        //TODO Bryta ut till mindre metoder
        return "updateBooking";
    }


}
