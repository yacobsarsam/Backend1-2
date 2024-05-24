package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.customers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CompanyCustomerService {
    public CustomerDto customersToCustomerDto(customers customer);
    public customers getCustomerDetailsById(Long id);
    public void addCustomerToDB(customers customer);
    public List<customers> searchCompanyClients(String searchWord, Sort sort);
}
