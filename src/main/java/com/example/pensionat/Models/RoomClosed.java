package com.example.pensionat.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomClosed extends RumEvent{
    private int roomNumber;
    private LocalDateTime eventTime;
    private String eventType;
    private String person;
}
