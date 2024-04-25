package com.example.pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bokning {

    @Id
    @GeneratedValue
    protected long id;
    protected LocalDate startdatum;
    protected LocalDate slutdatum;
    protected int numOfBeds;

    @ManyToOne
    @JoinColumn
    protected Kund kund;
    @ManyToOne
    @JoinColumn
    protected Rum rum;

    public Bokning(Kund kund, Rum rum, LocalDate startdatum, LocalDate slutdatum){
        this.startdatum = startdatum;
        this.slutdatum = slutdatum;
        this.kund = kund;
        this.rum = rum;
    }

}
