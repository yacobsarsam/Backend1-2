package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;

import java.util.List;

public interface KundService {

    /*

    public Kund kundDtoToKund(KundDto k);
    public DetailedKundDto kundToDetailedKundDto(Kund k);
    public List<DetailedKundDto> getAllCustomers();
    */
    boolean checkIfKundHasBokningar(Long kundId);
    public String deleteKund(long id);
    public List<KundDto> getAllKunder();
    public String addKund(Kund k);
    public Kund updateKund(long id);
    public KundDto checkIfKundExistByName(String name, String email, String telefon);
    public Kund kundDtoToKund(KundDto k);
    public KundDto kundToKundDto(Kund k);
    public DetailedKundDto kundToDetailedKundDto(Kund k);
    public List<DetailedKundDto> getAllKunder2();
    public Kund getKundById(Long id);
}
