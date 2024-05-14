package com.example.pensionat.Services.Imp;

import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListDataProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListDataProviderImp implements BlackListDataProvider {
    @Override
    public List<BlackListPerson> getAllBLKunder() throws IOException {
        JsonMapper jSonMapper = new JsonMapper();
        BlackListPerson[] blps = jSonMapper.readValue(new URL("https://javabl.systementor.se/api/stefan/blacklist"), BlackListPerson[].class);
        return List.of(blps);
    }
}
