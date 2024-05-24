package com.example.pensionat.Services;

import com.example.pensionat.Properties.ITProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class XmlURLProvider {


    private final ITProperties properties;

    public URL GetCompanyCustomersURL() throws MalformedURLException {
        return new URL(properties.getContractcustomer().getUrl());
    }
    public URL GetShippersURL() throws MalformedURLException {
        return new URL(properties.getShipper().getUrl());
    }
   }
