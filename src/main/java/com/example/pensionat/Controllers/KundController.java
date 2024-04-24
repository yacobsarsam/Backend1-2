package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "kunder")
public class KundController {

    private final KundService kundService;

    @RequestMapping("")
    public List<KundDto> getAllKunder(){
        return kundService.getAllKunder();
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
