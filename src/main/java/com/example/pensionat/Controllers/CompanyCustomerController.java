package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "company")
@RequiredArgsConstructor
public class CompanyCustomerController {

    private final CompanyCustomerService ccs;
    private final CustomerRepo customerRepo;

    @GetMapping("/customers/{id}")
    public String showIndividualCustomer(@PathVariable Long id, Model model) {
        customers customer = ccs.getCustomerDetailsById(id);
        model.addAttribute("customer", customer);
        return "visaCompanyCustomerIndividual.html";
    }

    @GetMapping("")
    public String showListOfCustomer(@RequestParam(defaultValue = "companyName") String sortField,
                                     @RequestParam(defaultValue = "asc") String sortOrder,
                                     @RequestParam(required = false) String searchWord,
                                     Model model) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);

        List<customers> allCustomers = customerRepo.findAll(sort); //Sort i CustomerRepo

        model.addAttribute("allCustomers", allCustomers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchWord", searchWord);

        return "visaAvtalsKunder.html";
    }

    @GetMapping("/search") //sorterar sökta kunder
    public String search(@RequestParam String searchWord,
                         @RequestParam(defaultValue = "companyName") String sortField,
                         @RequestParam(defaultValue = "asc") String sortOrder,
                         Model model) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);

        List<customers> companyClientMatch = ccs.searchCompanyClients(searchWord, sort);

        model.addAttribute("matchingCustomers", companyClientMatch);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        return "visaSoktaKunder.html";
    }
}
