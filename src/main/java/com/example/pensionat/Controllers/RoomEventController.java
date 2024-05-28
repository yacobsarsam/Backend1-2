package com.example.pensionat.Controllers;

import com.example.pensionat.Models.RumEvent;
import com.example.pensionat.Services.Imp.RoomEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomEventController {

    private final RoomEventService roomEventService;

    @GetMapping("/rum/{roomNumber}")
    public String getRoomEvents(@PathVariable int roomNumber, Model model) {
        List<RumEvent> events = roomEventService.getEventsByRoomNumber(roomNumber);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("events", events);
        return "room-events";
    }
}
