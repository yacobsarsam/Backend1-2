package com.example.pensionat.Services.Imp;

import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BlackListServiceImp implements BlackListService {

    @Override
    public List<BlackListPerson> getAllBLKunder() throws IOException {
            JsonMapper jSonMapper = new JsonMapper();
            BlackListPerson[] blps = jSonMapper.readValue(new URL("https://javabl.systementor.se/api/stefan/blacklist")
                    ,BlackListPerson[].class);
            return List.of(blps);
    }

    @Override
    public boolean isCustomerFieldsFilledAndCorrect(String name, String email, String group) {
        if (name.trim().isEmpty()|| group.trim().isEmpty()){
            return false;
        } else return !email.trim().isEmpty();
    }

    @Override
    public boolean checkIfBLKundExistByEmailUtanAttSkapa(String email) throws IOException {
        BlackListPerson blkund = getAllBLKunder().stream().filter(kund -> Objects.equals(kund.getEmail(), email)).findFirst().orElse(null);
        if(blkund == null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void AddBLKundToBlackList(String name, String email, String group) {
        //TODO PUT BLKund to
        try {
            // Ange URL för JSON-länken
            String urlString = "https://javabl.systementor.se/api/stefan/blacklist";

            // Skapa en URL-objekt från URL-strängen
            URL url = new URL(urlString);

            // Öppna en HTTP-anslutning till URL:en
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Ange att det är en POST-förfrågan
            connection.setRequestMethod("POST");

            // Aktivera utgående dataflöde (skicka data till servern)
            connection.setDoOutput(true);

            // Ange innehållstypen för förfrågan (JSON)
            connection.setRequestProperty("Content-Type", "application/json");

            // Skicka JSON-data som en del av förfråganens kropp (body)
            BlackListPerson data = GreateBlackListPerson(name,email,group);

            // Create an ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();

            // Convert the POJO object to a JSON string
            String jsonInputString = mapper.writeValueAsString(data);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Hämta svarskoden från servern
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Stäng anslutningen
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BlackListPerson GreateBlackListPerson(String name, String email, String group) {
        LocalDateTime d = LocalDateTime.now();
        BlackListPerson blp = new BlackListPerson(name,email,group, d, false);
        return blp;
    }


    public String getAllAvailableBLKundInfo(String name, String email, String group,  Model model) {
        String felmeddelande;
        if (!isCustomerFieldsFilledAndCorrect(name, email,group)){
            felmeddelande = "Fel i kund-fälten, kontrollera och försök igen";
            model.addAttribute("felmeddelande", felmeddelande);
            return addModelsAndReturn(name, email,group, model);
        }
        else
            return "visablkunder";
    }

    @Override
    public BlackListPerson getBlackListPrson(String email) throws IOException {
       List<BlackListPerson> bllist = getAllBLKunder();
       BlackListPerson bl = bllist.stream().filter(k->k.email.equals(email)).findFirst().get();
       return bl;
    }

    String addModelsAndReturn(String name, String email,String group, Model model){
        model.addAttribute("name", name);
        model.addAttribute("group", group);
        model.addAttribute("email", email);
        return "visablkunder";
    }

    @Override
    public String UpdateBLKund(BlackListPerson k, Model model) throws IOException {
        //if (isCustomerFieldsFilledAndCorrect(k.getNamn(), k.getTel(), k.getEmail())){

            if (true){
                // URL for the PUT request
                String url = "https://javabl.systementor.se/api/stefan/" + k.getEmail();

                // JSON data to be sent in the PUT request

                if (k.isOk()){
                    k.setOk(false);}
                else {
                    k.setOk(true);}
                String jsonInputString = "{  \"ok\":"+ k.isOk()+" }";


                // Create an instance of CloseableHttpClient
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    // Create an HttpPut request object with the URL
                    HttpPut httpPut = new HttpPut(url);

                    // Set the request body
                    StringEntity entity = new StringEntity(jsonInputString);
                    httpPut.setEntity(entity);

                    // Set the Content-Type header
                    httpPut.setHeader("Content-Type", "application/json");

                    // Execute the request
                    try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                        // Print the response status code
                        System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "updateBLKundDone";
            }

                /*
                // URL of the API endpoint
                String url = "http://javabl.systementor.se/api/stefan/blacklist/"+k.getEmail();


                if (k.isOk()){
                    k.setOk(false);}
                else {
                    k.setOk(true);}

                // JSON payload for updating the user
                String jsonInputString = "{ \"name\": sdfsd# , \"ok\":   k.isOk() }";

                // Set headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Set the request entity
                HttpEntity<String> requestEntity = new HttpEntity<>(jsonInputString, headers);

                // Create RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();

                // Perform the PUT request
                restTemplate.put(url, requestEntity);

            }*/

                /* USE CONNECTION
                // URL of the API endpoint

                String url = "https://javabl.systementor.se/api/stefan/blacklist/" + k.id;

                if (k.ok){
                    k.setOk(false);}
                else {
                    k.setOk(true);}

                // JSON payload for updating the user
                String jsonInputString ="{ \"ok\": " + k.isOk() + " }";
                // Create URL object
                URL obj = new URL(url);

                // Open connection
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // Set the request method to PUT
                con.setRequestMethod("PUT");

                // Set request headers
                con.setRequestProperty("Content-Type", "application/json");

                // Enable output and set payload
                con.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                    wr.writeBytes(jsonInputString);
                    wr.flush();
                }

                // Get response code
                int responseCode = con.getResponseCode();
                System.out.println("Response Code : " + responseCode);

                // Read response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // Print response
                    System.out.println(response.toString());
                }
            }*/

             else {
            model.addAttribute("felmeddelande", "Fel i kundfälten, kontrollera och försök igen");
            model.addAttribute("updatekund", k);
            return "updateblkund";
        }
    }
}
