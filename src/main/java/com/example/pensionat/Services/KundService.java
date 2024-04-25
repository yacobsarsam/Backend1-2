package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;

import java.util.List;

public interface KundService {

    public KundDto kundToKundDto(Kund k);
    public Kund kundDtoToKund(KundDto k);
    public DetailedKundDto KundToDetailedKundDto(Kund k);
    public List<DetailedKundDto> getAllCustomers();
    public List<KundDto> getAllKunder();
    public String addKund(Kund k);
    public String updateKund(KundDto k);
    public String deleteKund(long id);

}
