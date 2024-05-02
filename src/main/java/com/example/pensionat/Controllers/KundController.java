package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.BokningService;
import com.example.pensionat.Services.KundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "kunder")
public class KundController {

    private final KundRepo kundRepo;
    private final KundService kundService;
    private final BokningService bokningService;

   @RequestMapping("")
    public String getAllKunder(Model model){
        //TODO inväntar service-klassens funktion
        List<KundDto> allaKunder=kundService.getAllKunder();//getAllCustomers();
        model.addAttribute("allakunder", allaKunder);
        return "visakunder.html";
    }

    /*@RequestMapping("")
        public List<KundDto> getAllKunder(){
        return kundService.getAllKunder();
        }*/
/*    @GetMapping("/showBokingarById/{id}")
    public String showBookingDetails(@PathVariable Long id, Model model) {
        DetailedBokningDto booking = bokningService.getBookingDetailsById(id);
        model.addAttribute("booking", booking);
        return "bookingDetails.html";
    }*/

    @GetMapping("/showBokingarById/{id}")
    public String showBookingDetails(@PathVariable Long id, Model model) {
        List <BokningDto> allabokningar = bokningService.getAllBokningarbyId(id);
        List<Bokning> bokningar = bokningService.getAllBokningarAsBokningById(id);
        List<DetailedBokningDto> bok = bokningar.stream().map(bokningService::bokningToDetailedBokningDto).toList();
        model.addAttribute("allabokningar", allabokningar);
        model.addAttribute("id",id);
        model.addAttribute("bokningarAsBokning", bokningar);
        model.addAttribute("allaDetailedBokningar", bok);
        return "visabokningperkund.html";
    }

    @PostMapping("/registreraNyKund")
    public String createKund(@ModelAttribute KundDto kundDto, Model model) {
        kundService.getAllAvailableKundInfo(kundDto.getNamn(),kundDto.getTel(), kundDto.getEmail(),model);
        if(kundService.isCustomerFieldsFilledAndCorrect(kundDto.getNamn(),kundDto.getTel(), kundDto.getEmail())){
            String felmeddelande;
            if(kundService.checkIfKundExistByEmailUtanAttSkapa(kundDto.getNamn(), kundDto.getEmail(), kundDto.getTel())){
                felmeddelande = "Epost finns redan registrerad. Ny kund har inte skapats.";
                model.addAttribute("felmeddelande", felmeddelande);

                return getAllKunder(model);
            }
            else {
                kundService.checkIfKundExistByEmail(kundDto.getNamn(), kundDto.getEmail(), kundDto.getTel());
                felmeddelande = "Ny kund har skapats.";
                model.addAttribute("felmeddelande", felmeddelande);

                //felmeddelande
                //Kund kund = kundService.kundDtoToKund(kundDto);
                //kundService.addKund(kund);
                return getAllKunder(model);
            }
        }
        return getAllKunder(model);

//       return "visakunder";
    }

    //Ändrade från @DeleteMapping till @RequestMapping då det inte gick att testa innan
    //TODO MÅSTE uppdattera funktionen och kolla att bokningarna är framtida och inte utgått,
    // dvs matcha bokningarnas datum mot dagens datum
    @RequestMapping("/deleteById/{id}") //kollar först om kunden har bokningar annars raderas den om knappen trycks
    public String deleteKund(@PathVariable Long id) {
        boolean hasBokningar = kundService.checkIfKundHasBokningar(id);
        if (hasBokningar) {
            return "changenotdone";
            //return "redirect:/kunder";
        } else {
            kundService.deleteKund(id);
            return "changedone";
            //return "redirect:/kunder";
        }
    }
    @RequestMapping("/editById/{id}")
    public String EditKundInfo(@PathVariable Long id, Model model) {
        Kund k = kundService.updateKund(id);
        model.addAttribute("updatekund",k);
        return "updatekund";
    }
    @PostMapping("/update")
    public String updateKundinfo(Model model, Kund k){
        System.out.println(k.getNamn() + " " + k.getEmail() + " " + k.getTel() + " " + k.getId());
        List<KundDto> allaKunder=kundService.getAllKunder();//getAllCustomers();
        model.addAttribute("allakunder", allaKunder);
        return kundService.addKund(k, model);
        //return "redirect:/kunder";
    }

    //TODO saknas kommande metoder från Service klasserna


    /*
avvakta att deleta denna metod /Yacoub
//skapar kund från sidan "visakunder / Visa alla kunder" (utan bokning)

    @PostMapping("/create")
    public String createKundutanbokning(@RequestParam String namn, @RequestParam String tel, @RequestParam String email) {
        Kund kund = new Kund(namn, tel, email);
        kundService.addKund(kund);
        return "redirect:/kunder"; // Om du vill omdirigera till sidan för alla kunder efter att en ny kund har lagts till
    }*/


}

