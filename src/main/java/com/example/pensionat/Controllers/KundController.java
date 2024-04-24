package com.example.pensionat.Controllers;

import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KundController {

    KundRepo kundRepo;
    KundController(KundRepo kundRepo){
        this.kundRepo = kundRepo;
    }

    @RequestMapping("kunder")
    public List<Kund> getAllKunder(){
        //TODO inväntar service-klassens funktion
        return null;
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
