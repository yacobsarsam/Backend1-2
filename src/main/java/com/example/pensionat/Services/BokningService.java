package com.example.pensionat.Services;

import com.example.pensionat.Dtos.*;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Models.Rum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface BokningService {

    public BokningDto BokningToBokningDto(Bokning bokning);
    public Bokning detailedBokningDtoToBokning(DetailedBokningDto b, Kund kund, Rum rum);
    public DetailedBokningDto bokningToDetailedBokningDto(Bokning b);
    public List<DetailedBokningDto> getAllBokningar();
    //public String addBokning(Bokning b);
    public Bokning getBookingDetailsById(Long id);
    public String deleteBokning(long id);
    public Bokning newBokning(String namn, String tel, String email, LocalDate startdatum, LocalDate slutdatum, Long rumId, int numOfBeds);
    public List<BokningDto> getAllBokningarbyId(Long id);
    String addBokning();
    public String getAllAvailableRooms(Long id, String startDate, String endDate,
                                       String antalPersoner, Model model);
    public List<Bokning> getAllBokningar2();
    public Bokning updateBokning(Long id, LocalDate startDate, LocalDate endDate, int numOfBeds, Long rumId);
}
