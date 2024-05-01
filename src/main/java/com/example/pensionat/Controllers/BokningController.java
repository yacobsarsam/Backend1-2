package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.BokningRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.RumService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "bokningar")
@RequiredArgsConstructor
public class BokningController {

    private final BokningRepo bokningRepo;
    private final BokningService bokningService;
    private final KundController kundController;
    /*
    BokningController(BokningRepo bokningRepo, BokningService bokningService){
        this.bokningRepo = bokningRepo;
        this.bokningService = bokningService;

    //    private final BokningRepo bokningRepo;
    //    BokningController(BokningRepo bokningRepo, BokningService bokningService){
    //        this.bokningRepo = bokningRepo;
    //        this.bokningService = bokningService;
    //    }
*/
    @GetMapping("/updateBokning/{id}")
    public String updateInfo(@PathVariable Long id, Model model) {
        Bokning booking = bokningService.getBookingDetailsById(id);
        model.addAttribute("booking", booking);
        return "updateBooking.html";
    }

    @PostMapping("/update")
    public String makeBookingUpdate(Long bokId, Long rumId, String namn, String tel, String email, LocalDate startDate, LocalDate endDate, int numOfBeds, Model model){
        System.out.println("Kommer till update metoden");
        if (!(bokId == 0)) {
            Bokning b = bokningService.updateBokning(bokId, startDate, endDate, numOfBeds, rumId);
            model.addAttribute("booking", b);
            model.addAttribute("bookingDetailText", "Din bokning har blivit ändrad.");
            return "bookingDetails";
        }
        else {
            Bokning b = bokningService.newBokning(namn, tel, email, startDate, endDate, rumId, numOfBeds);
            model.addAttribute("booking", b);
            return "bookingDetails";
        }
    }



    @RequestMapping("")
    public String getAllBokningar(Model model){
        List<DetailedBokningDto> allBookings = bokningService.getAllBokningar();
        model.addAttribute("allBookings", allBookings);
        return "visaBokningar.html";
    }



    @PostMapping("/add")
    public String addBokning(String namn, String tel, String email, LocalDate startDate, LocalDate endDate, Long rumId, @RequestParam(defaultValue = "0") int numOfBeds, Model model){
        System.out.println("Num of beds in /add " + numOfBeds);
        Bokning b = bokningService.newBokning(namn, tel, email, startDate, endDate, rumId, numOfBeds);
        //DetailedBokningDto bdto = DetailedBokningDto.newBokning(namn, tel, email, startDate, endDate, rumId, numOfBeds);
        model.addAttribute("booking", b);
        return "bookingDetails";
    }

    @PostMapping("/delete/{id}")
    public String deleteBokning(@PathVariable Long id, Model model) {
        bokningService.deleteBokning(id);
        return kundController.showBookingDetails(id, model);
    }

    //TODO saknas mappings för att uppdatera en bokning, behöver kika på hur vi hanterar datumen i Boknings modellen
    //TODO saknas kommande metoder från Service klasserna
}
