package com.example.pensionat.Services.Impl;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RumServiceImpl implements RumService {

    private final RumRepo rumRepo;
    private final BokningRepo bokningRepo;

    @Override
    public String getAllAvailableRooms(String startDate, String endDate, String antalPersoner, Model model) {
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
            List<Long> notAva = getNonAvailableRoomsId(bokningRepo.findAll(), from, until);
            sortedRooms = rumRepo.findAll().stream().filter(rum -> rum.isDubbelrum() == needsDouble)
                    .filter(rum -> rum.getStorlek() >= neededSize)
                    .filter(rum -> notAva.stream().noneMatch(notAvaRum -> notAvaRum.equals(rum.getId()))).toList();
        } else {
            //TODO felhantering
            System.out.println("Inga eller bara ett datum valdes");
        }
        model.addAttribute("allRooms", sortedRooms);
        model.addAttribute("rubrik", "Lediga rum");
        model.addAttribute("roomType", roomType);

        //TODO sortera på bokning måste stämma med rums-id samt datumen. LocalDate parse?
        //TODO Bryta ut till mindre metoder
        return "addBokning";
    }

    List<Long> getNonAvailableRoomsId(List<Bokning> bokningar, LocalDate startDate, LocalDate endDate){
        List<Long> availableRooms = new ArrayList<>();

        for (Bokning bokning:bokningar
             ) {
            if (bokning.getStartdatum().isBefore(endDate) && bokning.getSlutdatum().isAfter(startDate)){
                System.out.println("Added to rooms: " + bokning.getId());
                availableRooms.add(bokning.getId());
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
