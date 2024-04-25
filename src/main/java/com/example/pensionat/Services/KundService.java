package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KundService {

    /*

    public Kund kundDtoToKund(KundDto k);
    public DetailedKundDto kundToDetailedKundDto(Kund k);
    public List<DetailedKundDto> getAllCustomers();
    */
    public List<KundDto> getAllKunder();
    public String addKund(Kund k);
    public String updateKund(KundDto k);
    public String deleteKund(long id);
    public KundDto checkIfKundExistByName(String name, String email, String telefon);
    public Kund kundDtoToKund(KundDto k);
    public KundDto kundToKundDto(Kund k);

}
