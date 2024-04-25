package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import com.example.pensionat.Services.KundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "kunder")
public class KundController {

    private final KundService kundService;
    private final KundRepo kundRepo;

    @RequestMapping("")
    public String getAllKunder(Model model){
        //TODO inväntar service-klassens funktion
        List<DetailedKundDto> allaKunder=kundService.getAllCustomers();
        model.addAttribute("allakunder", allaKunder);
        return "kunder.html";
    }

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
