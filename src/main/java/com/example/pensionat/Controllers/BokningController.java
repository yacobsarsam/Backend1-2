package com.example.pensionat.Controllers;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Services.BokningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bokningar")
@RequiredArgsConstructor
public class BokningController {


    private final BokningService bokningService;

    //    private final BokningRepo bokningRepo;
    //    BokningController(BokningRepo bokningRepo, BokningService bokningService){
    //        this.bokningRepo = bokningRepo;
    //        this.bokningService = bokningService;
    //    }

    @RequestMapping("/")
    public List<Bokning> getAllBokningar(){
        //TODO inväntar service-klassens funktion
        return null;
    }



    @PostMapping("/add")
    public String addBokning(@RequestBody Bokning bokning){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBokning(@PathVariable Long id){
        //TODO inväntar service-klassens funktion
        return null;
    }

    //TODO saknas mappings för att uppdatera en bokning, behöver kika på hur vi hanterar datumen i Boknings modellen
    //TODO saknas kommande metoder från Service klasserna
}
