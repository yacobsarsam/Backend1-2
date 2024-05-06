package com.example.pensionat;

import com.example.pensionat.Models.customers;
import com.example.pensionat.Models.allcustomers;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
@ComponentScan
public class CustomersConsoleApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        allcustomers customerList = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);

        for( customers s : customerList.customers ){
            System.out.println(s.contactName);
            System.out.println(s.country);
        }
        System.out.println("kör CustomersConsoleApplication");

    }
}
