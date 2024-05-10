package com.example.pensionat;

import com.example.pensionat.Models.Shippers;

import com.example.pensionat.Services.ShippersService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class ShippersConsoleApplication implements CommandLineRunner {

    private final ShippersService shippersService;

    @Override
    public void run(String... args) throws Exception {

        JsonMapper jSonMapper = new JsonMapper();
        Shippers[] shippers = jSonMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class);

        for (Shippers s : shippers) {
            shippersService.addShippersToDB(s);
        }

    }
}
