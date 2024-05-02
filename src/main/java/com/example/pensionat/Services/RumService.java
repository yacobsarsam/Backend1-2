package com.example.pensionat.Services;
import com.example.pensionat.Dtos.DetailedRumDto;
import com.example.pensionat.Models.Rum;
import java.util.List;
public interface RumService {

    public DetailedRumDto rumToDetailedRumDto(Rum r);
    public List<DetailedRumDto> getAllRum();
    public String addRum(Rum r);
    public String deleteRum(Long id);
    public Rum getRumById(Long id);
    public List<Rum> getAllRum2();
}
