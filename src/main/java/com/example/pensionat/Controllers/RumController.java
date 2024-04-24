package com.example.pensionat.Controllers;

import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RumController {

    RumRepo rumRepo;
    private final RumService rumService;
    RumController(RumRepo rumRepo, RumService rumService){
        this.rumRepo = rumRepo;
        this.rumService = rumService;
    }

    @RequestMapping("rum")
    public List<Rum> getAllRum(){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @RequestMapping("rum/lediga/period")
    public List<Rum> getAllLedigaRumByPeriod(@RequestParam String franDatum, @RequestParam String tillDatum, @RequestParam int antalPersoner ){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @RequestMapping("rum/lediga")
    public List<Rum> getAllLedigaRumByDate(@RequestParam String date, @RequestParam int antalPersoner){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @PostMapping("rum/add")
    public String addRum(@RequestBody Rum rum){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @DeleteMapping("rum/delete/{id}")
    public String deleteRum(@PathVariable Long id){
        //TODO inväntar service-klassens funktion
        return null;
    }

    //TODO saknas kommande metoder från Service klasserna
}
