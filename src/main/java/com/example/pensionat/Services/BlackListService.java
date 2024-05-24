package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BlackListPersonDto;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public interface BlackListService {
     public List<BlackListPersonDto> getAllBLKunder() throws IOException;

     boolean isCustomerFieldsFilledAndCorrect(String name, String email, String group);

     boolean checkIfBLKundExistByEmailUtanAttSkapa(String email) throws IOException;

     void AddBLKundToBlackList(String name, String email, String group);

     String getAllAvailableBLKundInfo(String name, String email, String group, Model model);

     BlackListPersonDto getBlackListPersonByEmail(String email) throws IOException;

     //String UpdateBLKund(BlackListPerson k, Model model) throws IOException;
     public void updateBlackListPerson(BlackListPersonDto person);
}
