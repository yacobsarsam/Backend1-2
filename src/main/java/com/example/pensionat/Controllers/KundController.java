package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.KundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class KundController {
    public final KundService kundService;
    KundRepo kundRepo;
    KundController(KundService kundService, KundRepo kundRepo){
        this.kundService = kundService;
        this.kundRepo = kundRepo;
    }

    @RequestMapping("kunder")
    public String getAllKunder(Model model){
        //TODO inväntar service-klassens funktion
        List<DetailedKundDto> allaKunder=kundService.getAllCustomers();
        model.addAttribute("allakunder", allaKunder);
        return "kunder.html";
    }

    @PostMapping("kunder/add")
    public String addKund(@RequestBody Kund kund){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @DeleteMapping("kunder/delete/{id}")
    public String deleteKund(@PathVariable Long id){
        //TODO inväntar service-klassens funktion
        return null;
    }

    //TODO saknas kommande metoder från Service klasserna
}
