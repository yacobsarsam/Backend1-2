package com.example.pensionat.Services.Imp;

import com.example.pensionat.Models.RumEvent;
import com.example.pensionat.Repositories.RumEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomEventService {

    private final RumEventRepository rumEventRepository;

    public List<RumEvent> getEventsByRoomNumber(int roomNumber) {
        return rumEventRepository.findByRoomNumber(roomNumber);
    }
}
