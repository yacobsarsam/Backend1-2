package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;

public interface CompanyCustomerService {
    public CustomerDto customersToCustomerDto(customers customer);
    public customers getCustomerDetailsById(Long id);
}
