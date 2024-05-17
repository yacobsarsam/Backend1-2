package com.example.pensionat;

import com.example.pensionat.Models.allcustomers;
import com.example.pensionat.Models.customers;
import com.example.pensionat.Services.CompanyCustomerService;
import com.example.pensionat.Services.XmlURLProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class CustomersConsoleApplication implements CommandLineRunner {
    private final CompanyCustomerService companyCustomerService;

    @Override
    public void run(String... args) throws Exception {
        XmlURLProvider xmlURLProvider = new XmlURLProvider();
    //     final CompanyCustomerService companyCustomerService;

            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            allcustomers customerList = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);

            for( customers c : customerList.customers ){
                companyCustomerService.addCustomerToDB(c);
//            System.out.println(c.contactName);
//            System.out.println(c.country);
            }
            System.out.println("kör CustomersConsoleApplication");

        }



}
