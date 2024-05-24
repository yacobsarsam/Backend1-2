package com.example.pensionat;

import com.example.pensionat.Services.ShippersService;
import com.example.pensionat.Services.XMLShippersProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippersConsoleApplication implements CommandLineRunner {

    private final ShippersService shippersService;
    private final XMLShippersProvider xmlShippersProvider;
    @Override
    public void run(String... args) throws Exception {
        xmlShippersProvider.GetShippersAsXMLAndSaveToDatabase();
    }
}
