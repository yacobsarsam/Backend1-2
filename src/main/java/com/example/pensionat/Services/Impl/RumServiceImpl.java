package com.example.pensionat.Services.Impl;

import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RumServiceImpl implements RumService {

    private final RumRepo rumRepo;
    private final BokningRepo bokningRepo;

    @Override
    public String getAllAvailableRooms(String startDate, String endDate, String antalPersoner, Model model) {
        //convert antalPersoner
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

        //TODO sortera på storlek kanske kanske med storleks attributet?
        //TODO sortera på bokning måste stämma med rums-id samt datumen. Localdate parse?

        //Hämta ut en lista på rum som är tillräckligt stora
        List<Rum> sortedRooms = rumRepo.findAll().stream().filter(rum -> rum.isDubbelrum() == needsDouble).
                filter(rum -> rum.getStorlek() >= neededSize).toList();

        model.addAttribute("allRooms", sortedRooms);
        model.addAttribute("rubrik", "Lediga rum");
        model.addAttribute("roomType", roomType);
        System.out.println("Model rubrik i rumserviceimpl" + model.getAttribute("rubrik"));
        return "addBokning";
    }
}
