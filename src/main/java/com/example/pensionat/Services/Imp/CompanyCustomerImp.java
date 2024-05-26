package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.BokningDto;
import com.example.pensionat.Dtos.CustomerDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Shippers;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Repositories.CustomerRepo;
import com.example.pensionat.Services.CompanyCustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyCustomerImp implements CompanyCustomerService {
    private final CustomerRepo cr;

    @Override
    public CustomerDto customersToCustomerDto(customers c) {
        return CustomerDto.builder().fax(c.getFax()).id(c.getId()).companyName(c.getCompanyName()).contactName(c.getContactName()).city(c.getCity())
                .country(c.getCountry()).build();
    }

    //TODO rename getCustomerById, vi hämtar bara kunden
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

    public List<customers> searchCompanyClients(String searchWord, Sort sort) {

        String lowerCaseSearchWord = searchWord;
        List<customers> companyClients = cr.findAll(sort); // sorterar sökt kunder
        List<customers> companyClientMatch = companyClients.stream()

                .filter(cc -> cc.getCompanyName().toLowerCase().contains(lowerCaseSearchWord) ||
                        cc.getContactName().toLowerCase().contains(lowerCaseSearchWord) ||
                        cc.getCity().toLowerCase().contains(lowerCaseSearchWord) ||
                        cc.getCountry().toLowerCase().contains(lowerCaseSearchWord))
                .collect(Collectors.toList());
        return companyClientMatch;
    }
}
