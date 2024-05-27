package com.example.pensionat.Models;

import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomOpened extends RumEvent{
    private int roomNumber;
    private LocalDateTime eventTime;
    private String eventType;
    private String person;
}
