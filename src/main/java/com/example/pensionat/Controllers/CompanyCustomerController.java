package com.example.pensionat.Controllers;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Dtos.DetailedBokningDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Services.CompanyCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "company")
@RequiredArgsConstructor
public class CompanyCustomerController {

    private final CompanyCustomerService ccs;

    @GetMapping("/customers/{id}")
    public String showIndividualCustomer(@PathVariable Long id, Model model) {
        customers customer = ccs.getCustomerDetailsById(id);
        model.addAttribute("customer", customer);
        return "visaCompanyCustomerIndividual.html";
    }

    @GetMapping("")
    public String showListOfCustomer(Model model) {
        List<CustomerDto> allCustomers = ccs.getAllCustomers();
        model.addAttribute("allCustomers", allCustomers);
        return "visaAvtalsKunder.html";
    }
}
