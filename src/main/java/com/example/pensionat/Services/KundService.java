package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;

public interface KundService {

    public KundDto kundToKundDto(Kund k);
    public Kund kundDtoToKund(KundDto k);
    public String addKund(KundDto k);
    public String updateKund(KundDto k);
    public String deleteKund(long id);
}
