package com.example.pensionat;

import com.example.pensionat.Services.Imp.BlackListDataProviderImp;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@AutoConfigureMockMvc
@SpringBootTest
public class DifferentIT {


    BlackListDataProviderImp blackListDataProviderImp =new BlackListDataProviderImp();
        @Test
        void GetBlackListPersonsWillFetch() throws MalformedURLException {
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
            assertTrue(jsonString.contains("ok"));
        }

}


