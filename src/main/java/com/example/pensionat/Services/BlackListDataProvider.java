package com.example.pensionat.Services;

import com.example.pensionat.Dtos.BlackListPersonDto;

import java.io.IOException;
import java.util.List;

public interface BlackListDataProvider {
    List<BlackListPersonDto> getAllBLKunder() throws IOException;
}
