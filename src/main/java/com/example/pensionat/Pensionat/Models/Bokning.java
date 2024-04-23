package com.example.pensionat.Pensionat.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bokning {

    @Id
    @GeneratedValue
    protected long id;
    protected String datum;
    @ManyToOne
    @JoinColumn
    protected Kund kund;
    @ManyToOne
    @JoinColumn
    protected Rum rum;

    public Bokning(String datum, Kund kund, Rum rum){
        this.datum = datum;
        this.kund = kund;
        this.rum = rum;
    }

}
