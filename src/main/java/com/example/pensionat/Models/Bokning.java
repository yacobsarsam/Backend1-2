package com.example.pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Bokning {

    @Id
    @GeneratedValue
    protected long id;
    protected String startdatum;
    protected String slutdatum;
    protected int numOfBeds;

    @ManyToOne
    @JoinColumn
    protected Kund kund;
    @ManyToOne
    @JoinColumn
    protected Rum rum;

    public Bokning(Kund kund, Rum rum, String startdatum, String slutdatum){
        this.startdatum = startdatum;
        this.slutdatum = slutdatum;
        this.kund = kund;
        this.rum = rum;
    }

}
