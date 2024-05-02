package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Dtos.KundDto;
import com.example.pensionat.Models.Kund;
import org.springframework.ui.Model;

import java.util.List;

public interface KundService {

    public boolean checkIfKundHasBokningar(Long kundId);
    public String deleteKund(long id);
    public List<KundDto> getAllKunder();
    public String addKund(Kund k, Model model);
    public Kund updateKund(long id);
    public KundDto checkIfKundExistByEmail(String name, String email, String telefon);
    public Kund kundDtoToKund(KundDto k);
    public KundDto kundToKundDto(Kund k);
    public DetailedKundDto kundToDetailedKundDto(Kund k);
    public List<DetailedKundDto> getAllKunder2();
    public Kund getKundById(Long id);

    boolean checkIfKundExistByEmailUtanAttSkapa(String namn, String email, String tel);
    String getAllAvailableKundInfo(String name, String telNr, String email, Model model);
    boolean isCustomerFieldsFilledAndCorrect(String namn, String tel, String email);
}
