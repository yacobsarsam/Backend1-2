package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BokingDto;
import com.example.pensionat.Models.Bokning;

public interface BokingService {

    public BokingDto BokningToBokningDto(Bokning bokning);
}
