package com.example.pensionat.Services;

import com.example.pensionat.Models.Shippers;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Service
public class XMLShippersProvider {
    private final ShippersService shippersService;
    private final XmlURLProvider xmlURLProvider;

    //private final ShippersService shippersService;


    public void GetShippersAsXMLAndSaveToDatabase() throws Exception {


        JsonMapper jSonMapper = new JsonMapper();
        Shippers[] shippers = jSonMapper.readValue(xmlURLProvider.GetShippersURL(), Shippers[].class);

        for (Shippers s : shippers) {
            shippersService.addShippersToDB(s);
        }

    }
}
