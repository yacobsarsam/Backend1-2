package com.example.pensionat.Pensionat.Controllers;

import com.example.pensionat.Pensionat.Models.Bokning;
import com.example.pensionat.Pensionat.Repositories.BokningRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BokningController {

    BokningRepo bokningRepo;
    BokningController(BokningRepo bokningRepo){
        this.bokningRepo = bokningRepo;
    }

    @RequestMapping("bokningar")
    public List<Bokning> getAllBokningar(){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @PostMapping("bokningar/add")
    public String addBokning(@RequestBody Bokning bokning){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @DeleteMapping("bokningar/delete/{id}")
    public String deleteBokning(@PathVariable Long id){
        //TODO inväntar service-klassens funktion
        return null;
    }

    //TODO saknas mappings för att uppdatera en bokning, behöver kika på hur vi hanterar datumen i Boknings modellen
    //TODO saknas kommande metoder från Service klasserna
}
