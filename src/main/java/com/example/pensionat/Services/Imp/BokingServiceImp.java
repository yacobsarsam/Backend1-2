package com.example.pensionat.Services.Imp;


import com.example.pensionat.Dtos.BokingDto;
import com.example.pensionat.Models.Bokning;
import com.example.pensionat.Services.BokingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BokingServiceImp implements BokingService {

    @Override
    public BokingDto BokningToBokningDto(Bokning b) {
        return BokingDto.builder().id(b.getId()).startdatum(b.getStartdatum()).
                slutdatum(b.getSlutdatum()).numOfBeds(b.getNumOfBeds()).build();
    }
}
