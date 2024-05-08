package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyCustomerImp implements CompanyCustomerService {
    CustomerRepo cr;

    @Override
    public CustomerDto customersToCustomerDto(customers c) {
        return CustomerDto.builder().companyName(c.getCompanyName()).contactName(c.getContactName()).country(c.getCountry()).build();
    }

    @Override
    public customers getCustomerDetailsById(Long id) {
        Optional<customers> optionalCustomers = cr.findById(id);
        if (optionalCustomers.isPresent()) {
            customers customer = optionalCustomers.get();
            return customer;
        } else {
            return null;
        }
    }
}
