package com.example.pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RumEvent {

    @Id
    @GeneratedValue
    protected long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rum_id", nullable = false)
    private Rum rum;

    protected LocalDate datum;
    protected String eventText;
}
