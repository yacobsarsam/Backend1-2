package com.example.pensionat;

import com.example.pensionat.Services.CompanyCustomerService;
import com.example.pensionat.Services.XMLCompanyCustomerProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomersConsoleApplication implements CommandLineRunner {

    private final XMLCompanyCustomerProvider xmlCompanyCustomerProvider;

    @Override
    public void run(String... args) throws Exception {
        xmlCompanyCustomerProvider.GetCompanyCustomersAsXMLAndSaveToDatabase();
    /*    XmlURLProvider xmlURLProvider = new XmlURLProvider();
    //     final CompanyCustomerService companyCustomerService;

            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            allcustomers customerList = xmlMapper.readValue(xmlURLProvider.GetCompanyCustomersURL(), allcustomers.class);

            for( customers c : customerList.customers ){
                companyCustomerService.addCustomerToDB(c);
//            System.out.println(c.contactName);
//            System.out.println(c.country);
            }
            System.out.println("kör CustomersConsoleApplication");

       */ }



}
