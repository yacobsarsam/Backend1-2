package com.example.pensionat.Services.Imp;


import com.example.pensionat.Dtos.DetailedKundDto;
import com.example.pensionat.Models.Kund;
import com.example.pensionat.Repositories.KundRepo;
import com.example.pensionat.Services.BokingService;
import com.example.pensionat.Services.KundService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class KundServiceImp implements KundService {
    private final KundRepo kundRepo;
    private final BokingService bokingService;

    @Override
    public List<DetailedKundDto> getAllCustomers() {
        return kundRepo.findAll().stream().map(k -> KundToDetailedKundDto(k)).toList();
    }

    @Override
    public DetailedKundDto KundToDetailedKundDto(Kund k) {
        return DetailedKundDto.builder().id(k.getId()).namn(k.getNamn()).tel(k.getTel()).
                email(k.getEmail()).bokingDtos(k.getBokning().stream().
                        map(bokning -> bokingService.BokningToBokningDto(bokning)).toList()).build();
    }
}
