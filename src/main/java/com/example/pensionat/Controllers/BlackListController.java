package com.example.pensionat.Controllers;


import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "blacklist")
public class BlackListController {
    //private BlackListRepo blackListRepo;
    private final BlackListService blackListService;
    @RequestMapping("")
    public String getAllBlKunder(Model model) throws IOException {
        //TODO inväntar service-klassens funktion
        List<BlackListPerson> allaBLKunder= blackListService.getAllBLKunder();
        model.addAttribute("allablkunder", allaBLKunder);
        return "visablkunder";
    }
    @PostMapping("/addtoblacklist")
    public String createKund(@ModelAttribute("name") String name , @ModelAttribute("email") String email,
                             @ModelAttribute("group")  String group, Model model) throws IOException {
        blackListService.getAllAvailableBLKundInfo(name,email, group,model);
        if(blackListService.isCustomerFieldsFilledAndCorrect(name,email,group)){
            String felmeddelande;
            if(blackListService.checkIfBLKundExistByEmailUtanAttSkapa(email)){
                felmeddelande = "Epost finns redan registrerad i black list. Kund har inte lagts till black list.";
                model.addAttribute("felmeddelande", felmeddelande);

                return getAllBlKunder(model);
            }
            else {
                blackListService.AddBLKundToBlackList(name, email, group);
                felmeddelande = "Kund har lagts till black list.";
                model.addAttribute("felmeddelande", felmeddelande);

                return getAllBlKunder(model);
            }
        }
        return getAllBlKunder(model);
    }
    @RequestMapping("/editByemail/{email}")
    public String EditKundInfo(@PathVariable String email, Model model) throws IOException {
        BlackListPerson blp = blackListService.getBlackListPrson(email);
        model.addAttribute("updateblkund",blp);
        return "updateblkund";
    }
    @PostMapping("/update")
    public String updateKundinfo(Model model, BlackListPerson k) throws IOException {
        blackListService.updateBlackListPerson(k);
        List<BlackListPerson> allaBLKunder = blackListService.getAllBLKunder();
        model.addAttribute("allakunder", allaBLKunder);
        return "updateBLKundDone";
    }
}
