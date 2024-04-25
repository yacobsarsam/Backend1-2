package com.example.pensionat.Services.Impl;

import com.example.pensionat.Services.BokningService;
import org.springframework.stereotype.Service;

@Service
public class BokningServiceImpl implements BokningService {
    @Override
    public String addBokning() {
        return "addBokning";
    }
}
