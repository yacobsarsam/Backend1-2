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

    @Override
    public void run(String... args) throws Exception {


        XMLShippersProvider xmlShippersProvider =new XMLShippersProvider(shippersService);
xmlShippersProvider.GetShippersAsXMLAndSaveToDatabase();
        /*JsonMapper jSonMapper = new JsonMapper();
        Shippers[] shippers = jSonMapper.readValue(new URL("https://javaintegration.systementor.se/shippers"), Shippers[].class);

        for (Shippers s : shippers) {
            shippersService.addShippersToDB(s);
        }*/

    }
}
