package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.RumRepo;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import com.example.pensionat.Services.RumService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "rum")
public class RumController {


    private final RumService rumService;


    @RequestMapping("")
    public List<DetailedRumDto> getAllRum(){
        return rumService.getAllRum();
    }

    @RequestMapping("/lediga/period")
    public List<Rum> getAllLedigaRumByPeriod(@RequestParam String franDatum, @RequestParam String tillDatum, @RequestParam int antalPersoner ){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @RequestMapping("/lediga")
    public List<Rum> getAllLedigaRumByDate(@RequestParam String date, @RequestParam int antalPersoner){
        //TODO inväntar service-klassens funktion
        return null;
    }

    @PostMapping("/add")
    public String addRum(@RequestBody Rum rum){
        return rumService.addRum(rum);
    }

    @RequestMapping("/delete/{id}")
    public String deleteRum(@PathVariable Long id){
        return rumService.deleteRum(id);
    }

    //TODO saknas kommande metoder från Service klasserna
}
