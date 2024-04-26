package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "kunder")
public class KundController {

    private final KundService kundService;
    private final BokningService bokningService;

   @RequestMapping("")
    public String getAllKunder(Model model){
        //TODO inväntar service-klassens funktion
        List<KundDto> allaKunder=kundService.getAllKunder();//getAllCustomers();
        model.addAttribute("allakunder", allaKunder);
        return "visakunder.html";
    }

    /*@RequestMapping("")
        public List<KundDto> getAllKunder(){
        return kundService.getAllKunder();
        }*/
    @GetMapping("/showBokingarById/{id}")
    public String showBookingDetails(@PathVariable Long id, Model model) {
        DetailedBokningDto booking = bokningService.getBookingDetailsById(id);
        model.addAttribute("booking", booking);
        return "bookingDetails.html";
    }
    @PostMapping("/registreraNyKund")
    public String createKund(@ModelAttribute KundDto kundDto) {
        Kund kund = kundService.kundDtoToKund(kundDto);
        kundService.addKund(kund);
        return "redirect:/kunder"; // Om du vill omdirigera till sidan för alla kunder efter att en ny kund har lagts till
    }
    //Ändrade från @DeleteMapping till @RequestMapping då det inte gick att testa innan
    @RequestMapping("/deleteById/{id}") //kollar först om kunden har bokningar annars raderas den om knappen trycks
    public String deleteKund(@PathVariable Long id) {
        boolean hasBokningar = kundService.checkIfKundHasBokningar(id);
        if (hasBokningar) {
            return "redirect:/kunder";
        } else {
            kundService.deleteKund(id);
            return "redirect:/kunder";
        }
    }


    //TODO saknas kommande metoder från Service klasserna
}
