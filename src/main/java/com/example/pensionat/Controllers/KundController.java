package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "kunder")
public class KundController {

    private final KundService kundService;

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

    @PostMapping("/add")
    public String addKund(@RequestBody Kund kund){
        return kundService.addKund(kund);
    }

    //Ändrade från @DeleteMapping till @RequestMapping då det inte gick att testa innan
    @RequestMapping("/delete/{id}")
    public String deleteKund(@PathVariable Long id){
        return kundService.deleteKund(id);
    }

    //TODO saknas kommande metoder från Service klasserna
}
