package com.example.pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Bokning {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected LocalDate startdatum;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected LocalDate slutdatum;

    @Column(nullable = false)
    protected int numOfBeds;

    @ManyToOne
    protected Kund kund;
    @ManyToOne
    protected Rum rum;

    protected int totalPrice;

    public Bokning(Kund kund, Rum rum, LocalDate startdatum, LocalDate slutdatum, int numOfBeds, int totalPrice){
        this.startdatum = startdatum;
        this.slutdatum = slutdatum;
        this.kund = kund;
        this.rum = rum;
        this.numOfBeds = numOfBeds;
        this.totalPrice = totalPrice;
    }
    public Bokning(Kund kund, Rum rum, LocalDate startdatum, LocalDate slutdatum, int numOfBeds){
        this.startdatum = startdatum;
        this.slutdatum = slutdatum;
        this.kund = kund;
        this.rum = rum;
        this.numOfBeds = numOfBeds;
    }
    public Bokning( Rum rum, LocalDate startdatum, LocalDate slutdatum, int numOfBeds){
        this.startdatum = startdatum;
        this.slutdatum = slutdatum;
        this.rum = rum;
        this.numOfBeds = numOfBeds;
    }
}
