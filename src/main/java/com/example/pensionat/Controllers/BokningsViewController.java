package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import com.example.pensionat.Services.RumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class BokningsViewController {
    private final BokningService bokningService;
    private final RumService rumService;
    private final KundService kundService;

    @RequestMapping("/book")
    public String addBokningSite(){
        return bokningService.addBokning();
    }

    @GetMapping("book/viewRooms")
    public String showAllRooms(@RequestParam(defaultValue = "0") Long bokId, @RequestParam(defaultValue = "0") Long rumId, @RequestParam String namn, @RequestParam String telNr,
                               @RequestParam String email, @RequestParam String startDate, @RequestParam String endDate,
                               @RequestParam(defaultValue = "1") int antalPersoner, Model model){
        return bokningService.getAllAvailableRooms(bokId, rumId, namn, telNr, email, startDate, endDate, antalPersoner, model);
    }

    @GetMapping("book/update/viewRooms")
    public String showAllRooms2(@RequestParam Long bokId, Long rumId, @RequestParam String namn, @RequestParam String telNr,
                                @RequestParam String email, @RequestParam String startDate, @RequestParam String endDate,
                                @RequestParam int antalPersoner, Model model){
        return bokningService.getAllAvailableRooms(bokId, rumId, namn, telNr, email, startDate, endDate, antalPersoner, model);
    }

    @RequestMapping("/nyBokning{id}")
    public String addBokningSite2(@RequestParam Long id, Model model) {
        Kund kund = kundService.getKundById(id);
        if (kund == null) {
            return "customerNotFound";
        }
        model.addAttribute("id", id);
        model.addAttribute("name", kund.getNamn());
        model.addAttribute("telNr", kund.getTel());
        model.addAttribute("email", kund.getEmail());

        if (!kund.getBokning().isEmpty()) {
            Bokning senasteBokning = kund.getBokning().get(kund.getBokning().size() - 1);
            model.addAttribute("startDate", senasteBokning.getStartdatum());
            model.addAttribute("endDate", senasteBokning.getSlutdatum());
        } else {
            LocalDate idag = LocalDate.now();
            model.addAttribute("startDate", idag);
            model.addAttribute("endDate", idag);
        }
        bokningService.addBokning();
        return "makeBookingWithCustomer.html";
    }
}
