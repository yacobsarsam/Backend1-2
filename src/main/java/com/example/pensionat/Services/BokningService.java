package com.example.pensionat.Services;

import com.example.pensionat.Dtos.*;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;

import java.time.LocalDate;
import java.util.List;

public interface BokningService {

    public BokningDto BokningToBokningDto(Bokning bokning);
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum);
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);
    public List<DetailedBokningDto> getAllBokningar();
    //public String addBokning(Bokning b);
    public String updateBokning(BokningDto b);
    DetailedBokningDto getBookingDetailsById(Long id);
    public String deleteBokning(long id);
    public String newBokning(String namn, String tel, String email, LocalDate startdatum, LocalDate slutdatum, Long rumId, int numOfBeds);

    String addBokning();
}
