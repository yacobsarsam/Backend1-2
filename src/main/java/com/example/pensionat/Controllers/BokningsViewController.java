package com.example.pensionat.Controllers;

import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                               @RequestParam int antalPersoner, Model model){
        return rumService.getAllAvailableRooms(namn, telNr, email, startDate, endDate, antalPersoner, model);
    }
}
