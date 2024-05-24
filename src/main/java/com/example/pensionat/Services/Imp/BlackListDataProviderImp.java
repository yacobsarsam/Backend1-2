package com.example.pensionat.Services.Imp;

import com.example.pensionat.Dtos.BlackListPersonDto;
import com.example.pensionat.Properties.ITProperties;
import com.example.pensionat.Services.BlackListDataProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListDataProviderImp implements BlackListDataProvider {

    private final ITProperties properties;

    @Override
    public List<BlackListPersonDto> getAllBLKunder() throws IOException {
        JsonMapper jSonMapper = new JsonMapper();
        BlackListPersonDto[] blps = jSonMapper.readValue(GetBlackListPersonURL(), BlackListPersonDto[].class);
        return List.of(blps);
    }

    public URL GetBlackListPersonURL() throws MalformedURLException {
        return new URL(properties.getBlacklist().getUrl());
    }
}
