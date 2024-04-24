package com.example.pensionat.Services;

import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Models.Kund;

import java.util.List;

public interface KundService {
    public List<DetailedKundDto> getAllCustomers();
    DetailedKundDto KundToDetailedKundDto(Kund k);
}
