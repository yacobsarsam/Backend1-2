package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Dtos.RumDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RumServiceImp implements RumService {

    //private final RumRepo rr;
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
        return rumRepo.findAll().stream().map(rum -> rumToDetailedRumDto(rum)).toList();
    }

    @Override
    public List<Rum> getAllRum2() {
        return rumRepo.findAll();
    }

    @Override
    public String addRum(Rum r) {
        rumRepo.save(r);
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

    public Rum getRumById(Long id) {
        return rumRepo.findById(id).get();
    }

    @Override
    public String deleteRum(Long id) {
        Rum rum = rumRepo.findById(id).get();
        rumRepo.deleteById(id);
        return "Rum nr " + rum.getRumsnr() + " har raderats.";

    }

    @Override
    public String getAllAvailableRooms(Long bokId, Long rumId, String name, String telNr, String email,
                                       String startDate, String endDate, int antalPersoner, Model model) {
        String felmeddelande;
        if (!isCustomerFieldsFilledAndCorrect(name, telNr, email)) {
            felmeddelande = "Fel i kund-fälten, kontrollera och försök igen";
            model.addAttribute("felmeddelande", felmeddelande);
            return addModelsAndReturn(name, telNr, email, startDate, endDate, antalPersoner, model);
        }

        Kund kund = kundService.kundDtoToKund(kundService.checkIfKundExistByEmail(name, email, telNr));

        //Kolla vilken storlek på rum som kan visas
        boolean needsDouble = antalPersoner > 1;
        int neededSize = antalPersoner - 1;
        String roomType;
        if (needsDouble) {
            roomType = "Dubbelrum";
        } else {
            roomType = "Enkelrum";
        }
        //get the dates:
        List<Long> ledigaRumsId = new ArrayList<>();
        List<Rum> sortedRooms = new ArrayList<>();
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            LocalDate from = LocalDate.parse(startDate);
            LocalDate until = LocalDate.parse(endDate);
            //kontroll för att slut datum är EFTER startdatum
            if (!from.isBefore(until)) {
                felmeddelande = "Fel i datumen, du har valt ett till-datum som är före från-datum";
                System.out.println("Fel i datumen, du har valt ett till-datum som är före från-datum");
                model.addAttribute("felmeddelande", felmeddelande);
                return addModelsAndReturn(name, telNr, email, startDate, endDate, antalPersoner, model);
            } else if (from.isBefore(LocalDate.now())) {
                //kontroll att start datumet inte has passerat redan
                felmeddelande = "Fel i datumen, du har valt ett datum som redan passerat";
                System.out.println("Fel i datumen, du har valt ett datum som redan passerat");
                model.addAttribute("felmeddelande", felmeddelande);
                return addModelsAndReturn(name, telNr, email, startDate, endDate, antalPersoner, model);
            }

            //Hämta ut alla rums-id som inte är bokade under det spannet som angets
            List<Long> notAva = getNonAvailableRoomsId(bokningRepo.findAll(), from, until);
            sortedRooms = rumRepo.findAll().stream().filter(rum -> rum.isDubbelrum() == needsDouble)
                    .filter(rum -> rum.getStorlek() >= neededSize)
                    .filter(rum -> notAva.stream().noneMatch(notAvaRum -> notAvaRum.equals(rum.getId()))).toList();

            model.addAttribute("allRooms", sortedRooms);
            model.addAttribute("rubrik", "Lediga rum");
            model.addAttribute("roomType", roomType);
            model.addAttribute("name", kund.getNamn());
            model.addAttribute("telNr", kund.getTel());
            model.addAttribute("email", kund.getEmail());
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("antalPersoner", antalPersoner);

            //TODO Bryta ut till mindre metoder
            return "addBokning";

        } else {
            //TODO felhantering
            felmeddelande = "Inga eller bara ett datum valdes";
            System.out.println("Inga eller bara ett datum valdes");
            model.addAttribute("felmeddelande", felmeddelande);
            return "addBokning";
        }

    }

    public List<Long> getNonAvailableRoomsId(List<Bokning> bokningar, LocalDate startDate, LocalDate endDate) {
        List<Long> availableRooms = new ArrayList<>();

        for (Bokning bokning : bokningar
        ) {
            if (bokning.getStartdatum().isBefore(endDate) && bokning.getSlutdatum().isAfter(startDate)) {
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

    boolean roomIdExistInList(Long roomId, List<Long> nonAvailableID) {
        return nonAvailableID.stream().anyMatch(id -> id.equals(roomId));
    }

    public boolean isCustomerFieldsFilledAndCorrect(String name, String telnr, String email) {
        if (name.trim().isEmpty()) {
            return false;
        } else if (telnr.trim().length() < 10 && !telnr.trim().matches("[0-9+-}+]")) {
            return false;
        } else return !email.trim().isEmpty();
    }

    String addModelsAndReturn(String name, String telnr, String email, String startDate, String endDate, int antalPersoner, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("telNr", telnr);
        model.addAttribute("email", email);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("antalPersoner", antalPersoner);
        return "addBokning";
    }
}
