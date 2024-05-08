package com.example.pensionat;

import com.example.pensionat.Models.Shippers;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class ShippersConsoleApplication implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

//        JsonFactory module = new JsonFactory();
        JsonMapper jSonMapper = new JsonMapper();
        Shippers[] shippers = jSonMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class);

        for (Shippers s: shippers) {
            System.out.println(s.getCompanyName());



        }
//        allcustomers customerList = JsonMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), allcustomers.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        Shippers shippersList = mapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers.class);

//        for (Shippers s: shippersList) {
//
//            System.out.println(s.getCompanyName());
//
//        }

    }
}
