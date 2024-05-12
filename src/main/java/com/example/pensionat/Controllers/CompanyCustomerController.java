package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "25") int size,
                                     Model model) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        List<customers> allCustomers = customerRepo.findAll(sort);
        Page<customers> clientsPage;
        clientsPage = customerRepo.findAll(pageable);

        model.addAttribute("clientsPage", clientsPage);
        model.addAttribute("allCustomers", allCustomers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchWord", searchWord);

        return "visaAvtalsKunder.html";
    }



    @GetMapping("/search")
    public String search(@RequestParam String searchWord,
                         @RequestParam(defaultValue = "companyName") String sortField,
                         @RequestParam(defaultValue = "asc") String sortOrder,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "25") int size,
                         Model model) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        List<customers> customersMatch = ccs.searchCompanyClients(searchWord, sort);

        int start = Math.min((page - 1) * size, customersMatch.size());
        int end = Math.min(start + size, customersMatch.size());
        List<customers> pageResults = customersMatch.subList(start, end);

        Page<customers> clientsPage = new PageImpl<>(pageResults, pageable, customersMatch.size());
        //skapar nya pages för passande search results

        model.addAttribute("clientsPage", clientsPage);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        return "visaSoktaKunder.html";
    }
}