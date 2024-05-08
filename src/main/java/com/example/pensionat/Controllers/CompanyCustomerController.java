package com.example.pensionat.Controllers;

import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Services.CompanyCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
