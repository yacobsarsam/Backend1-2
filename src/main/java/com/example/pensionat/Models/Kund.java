package com.example.pensionat.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kund {

    @Id
    @GeneratedValue
    protected long id;
    protected String namn;
    protected String tel;

    public Kund(String namn, String tel){
        this.namn = namn;
        this.tel = tel;
    }
}
