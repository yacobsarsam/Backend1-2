package com.example.pensionat.Services;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public interface RumService {

    String getAllAvailableRooms(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String antalPersoner, Model model);
}
