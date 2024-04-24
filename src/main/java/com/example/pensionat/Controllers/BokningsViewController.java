package com.example.pensionat.Controllers;

import com.example.pensionat.Services.BokningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BokningsViewController {
    private final BokningService bokningService;

    @RequestMapping("/book")
    public String addBokningSite(){
        return bokningService.addBokning();
    }
}
