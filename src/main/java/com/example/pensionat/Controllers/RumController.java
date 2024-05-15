package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Models.RumEvent;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "rum")
public class RumController {


    private final RumService rumService;


    @RequestMapping("")
    public List<DetailedRumDto> getAllRum(){
        return rumService.getAllRum();
    }

    @GetMapping("/allRooms")
    public String showAllRooms(Model model) {
        List<DetailedRumDto> allRum = rumService.getAllRum();
        model.addAttribute("allRum", allRum);
        return "allRooms";
    }
    @GetMapping("/{roomId}/events")
    public String showRoomEvents(@PathVariable Long roomId, Model model) {
        Rum room = rumService.getRumById(roomId);
        List<RumEvent> roomEvents = rumService.getEventsForRum(roomId);
        model.addAttribute("room", room);
        model.addAttribute("events", roomEvents);
        return "rumEvents";
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
