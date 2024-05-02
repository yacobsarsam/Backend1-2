package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Services.BokningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "bokningar")
@RequiredArgsConstructor
public class BokningController {

    private final BokningService bokningService;
    private final KundController kundController;

    @GetMapping("/updateBokning/{id}")
    public String updateInfo(@PathVariable Long id, Model model) {
        Bokning booking = bokningService.getBookingDetailsById(id);
        model.addAttribute("booking", booking);
        return "updateBooking.html";
    }

    @PostMapping("/update")
    public String makeBookingUpdate(Long bokId, Long rumId, LocalDate startDate, LocalDate endDate, @RequestParam(defaultValue = "0") int extraBeds, int antalPersoner, Model model){
        Bokning b = bokningService.updateBokning(bokId, startDate, endDate, extraBeds, rumId);
        model.addAttribute("booking", b);
        model.addAttribute("rumInfo", b.getRum());
        model.addAttribute("kundInfo", b.getKund());
        model.addAttribute("bookingDetailText", "Din bokning har blivit ändrad.");
        return "bookingDetails";
    }

    @RequestMapping("")
    public String getAllBokningar(Model model){
        List<DetailedBokningDto> allBookings = bokningService.getAllBokningar();
        model.addAttribute("allBookings", allBookings);
        return "visaBokningar.html";
    }

    @PostMapping("/add")
    public String addBokning(String namn, String tel, String email, LocalDate startDate, LocalDate endDate, Long rumId, @RequestParam(defaultValue = "0") int extraBeds, @RequestParam int antalPersoner, Model model){
        Bokning b = bokningService.newBokning(namn, tel, email, startDate, endDate, rumId, extraBeds);
        model.addAttribute("booking", b);
        model.addAttribute("rumInfo", b.getRum());
        model.addAttribute("kundInfo", b.getKund());
        return "bookingDetails";
    }

    @PostMapping("/delete/{id}")
    public String deleteBokning(@PathVariable Long id, Model model) {
        bokningService.deleteBokning(id);
        return kundController.showBookingDetails(id, model);
    }
}
