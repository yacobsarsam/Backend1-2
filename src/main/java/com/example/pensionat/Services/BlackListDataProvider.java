package com.example.pensionat.Services;

import com.example.pensionat.Models.BlackListPerson;

import java.io.IOException;
import java.util.List;

public interface BlackListDataProvider {
    List<BlackListPerson> getAllBLKunder() throws IOException;
}
