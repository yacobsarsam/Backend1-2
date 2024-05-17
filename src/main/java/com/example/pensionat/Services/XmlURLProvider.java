package com.example.pensionat.Services;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class XmlURLProvider {


    public URL GetCompanyCustomersURL() throws MalformedURLException {
        URL url = new URL("https://javaintegration.systementor.se/customers");
        return url;
    }
    public URL GetShippersURL() throws MalformedURLException {
        URL url =  new URL("https://javaintegration.systementor.se/shippers");
        return url;
    }
   }
