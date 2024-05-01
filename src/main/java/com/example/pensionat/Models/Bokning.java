package com.example.pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
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
    //yacoub comment bort @JoinColumn
    protected Kund kund;
    @ManyToOne
    //yacoub comment bort @JoinColumn
    protected Rum rum;

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
