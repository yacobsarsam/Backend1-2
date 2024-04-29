package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BokningsViewController {
    private final BokningService bokningService;
    private final RumService rumService;

    @RequestMapping("/book")
    public String addBokningSite(){
        return bokningService.addBokning();
    }

    @GetMapping("book/viewRooms")
    public String showAllRooms(@RequestParam String namn, @RequestParam String telNr, @RequestParam String email,
                               @RequestParam String startDate, @RequestParam String endDate,
                               @RequestParam String antalPersoner, Model model){
        return rumService.getAllAvailableRooms(namn, telNr, email, startDate, endDate, antalPersoner, model);
    }

    @GetMapping("book/update/viewRooms")
    public String showAllRooms2(@RequestParam Long bokId, Long rumId, @RequestParam String startDate, @RequestParam String endDate,
                                @RequestParam String antalPersoner, Model model){
        return bokningService.getAllAvailableRooms(bokId, rumId, startDate, endDate, antalPersoner, model);
    }

}
