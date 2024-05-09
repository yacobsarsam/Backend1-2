package com.example.pensionat.Services.Imp;

import com.example.pensionat.Models.BlackListPerson;
import com.example.pensionat.Services.BlackListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
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
            /*String jsonInputString = "{\"id\" : \"" + id + "\" ,
                    \"name\" : \"" + name + "\" ,
                    \"email\" : \"" + email + "\" ,
                    \"created\" : \"" + date + "\",
                    \"ok\" : \"" + ok + "\"}";*/
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
    String addModelsAndReturn(String name, String email,String group, Model model){
        model.addAttribute("name", name);
        model.addAttribute("group", group);
        model.addAttribute("email", email);
        return "visablkunder";
    }
}
