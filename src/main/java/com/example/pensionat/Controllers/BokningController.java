package com.example.pensionat.Controllers;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Services.BokningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "bokningar")
@RequiredArgsConstructor
public class BokningController {

    private final BokningRepo bokningRepo;
    private final BokningService bokningService;
    /*
    BokningController(BokningRepo bokningRepo, BokningService bokningService){
        this.bokningRepo = bokningRepo;
        this.bokningService = bokningService;
    }


     */

    @RequestMapping("/")
    public List<Bokning> getAllBokningar(){
        //TODO inväntar service-klassens funktion
        return null;
    }



    @PostMapping("/add")
    public String addBokning(String namn, String tel, String email, LocalDate startDate, LocalDate endDate, Long rumId, @RequestParam(defaultValue = "0") int numOfBeds, Model model){
        System.out.println("Num of beds in /add " + numOfBeds);
        Bokning b = bokningService.newBokning(namn, tel, email, startDate, endDate, rumId, numOfBeds);
        //DetailedBokningDto bdto = DetailedBokningDto.newBokning(namn, tel, email, startDate, endDate, rumId, numOfBeds);
        model.addAttribute("booking", b);
        return "bookingDetails";
    }

    @PostMapping("/delete/{id}")
    public String deleteBokning(@PathVariable Long id) {
        bokningService.deleteBokning(id);
        return "visabokningperkund";
    }

    //TODO saknas mappings för att uppdatera en bokning, behöver kika på hur vi hanterar datumen i Boknings modellen
    //TODO saknas kommande metoder från Service klasserna
}
