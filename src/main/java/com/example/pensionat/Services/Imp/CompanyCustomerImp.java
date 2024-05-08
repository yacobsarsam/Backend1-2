package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Shippers;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyCustomerImp implements CompanyCustomerService {
    private final CustomerRepo cr;

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

    @Override
    public void addCustomerToDB(customers customer) {
        customers foundCustomer;
        foundCustomer = cr.findAll().stream().filter(c -> Objects.equals(c.getPhone(), customer.getPhone())).findFirst().orElse(null);

        if (foundCustomer==null) {
            cr.save(customer);
            System.out.println(" --- En ny företagskund har lagts till: \"" + customer.getContactName() + ", " + customer.getContactTitle() + "\" --- ");
        }
        else {
            System.out.println( "Företagskunden " + customer.getCompanyName() + " fanns redan.");
        }
    }
}
