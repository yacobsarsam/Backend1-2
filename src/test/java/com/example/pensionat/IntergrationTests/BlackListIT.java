package com.example.pensionat.IntergrationTests;

import com.example.pensionat.Services.Imp.BlackListDataProviderImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BlackListIT {

    @Autowired
    BlackListDataProviderImp blackListDataProviderImp;
    @Test
    void GetBlackListPersonsWillFetch() throws IOException {
        Scanner s = new Scanner(blackListDataProviderImp.GetBlackListPersonURL().openStream()).useDelimiter("//A");
        String result = s.hasNext() ? s.next() : "";
        System.out.println("Result från inläsning BlackLIst : "+result);
        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("name"));
        assertTrue(result.contains("group"));
        assertTrue(result.contains("created"));
        assertTrue(result.contains("ok"));
            /*
            String jsonString = "";
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try {
                HttpGet request = new HttpGet(String.valueOf(blackListDataProviderImp.GetBlackListPersonURL()));
                CloseableHttpResponse response = httpClient.execute(request);
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                         jsonString = EntityUtils.toString(entity);
                        System.out.println("JSONSTRING= " +jsonString);
                    }
                } finally {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            assertTrue(jsonString.contains("id"));
            assertTrue(jsonString.contains("email"));
            assertTrue(jsonString.contains("name"));
            assertTrue(jsonString.contains("group"));
            assertTrue(jsonString.contains("created"));
            assertTrue(jsonString.contains("ok"));*/
    }
    /*@Test
    void FetchAndSaveCustomersShouldSaveToDatabase() throws Exception {

        XmlURLProvider xmlURLProvider = mock(XmlURLProvider.class);
        URL mockUrl = mock(URL.class);
        when(xmlURLProvider.GetCompanyCustomersURL()).thenReturn(mockUrl);

        // Load the XML file using the class loader
        InputStream inputStream = XmlURLProvider.class.getClassLoader().getResourceAsStream("companyCustomers.xml");

        // Mock the behavior of openStream() method of the URL object
        when(mockUrl.openStream()).thenReturn(inputStream);



        //Arrange
        XmlURLProvider xmlURLProvider = mock(XmlURLProvider.class);
        when(xmlURLProvider.GetCompanyCustomersURL().openStream()).thenReturn(getClassLoader().
                getResourceAsStream("companyCustomers.xml"));
        //InputStream inputStream = XmlURLProvider.class.getClassLoader().getResourceAsStream("companyCustomers.xml");

        sut =  new XMLCompanyCustomerProvider(companyCustomerService, xmlURLProvider);
/*
        //Act
        //customerRepo.deleteAll();

        //sut.GetCompanyCustomersAsXMLAndSaveToDatabase();
        //Assert
        assertEquals(3,customerRepo.count());
        }*/
}
