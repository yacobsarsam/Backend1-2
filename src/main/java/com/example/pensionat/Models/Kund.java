package com.example.pensionat.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Kund {

    @Id
    @GeneratedValue
    protected long id;
    protected String namn;
    protected String tel;
    protected String email;

    @OneToMany(mappedBy = "kund")
    protected List<Bokning> bokning;

    public Kund(String namn, String tel, String email){
        this.namn = namn;
        this.tel = tel;
        this.email = email;
    }

    public Kund(String namn, String tel, String email, List<Bokning> emptyList) {
        this.namn = namn;
        this.tel = tel;
        this.email = email;
        this.bokning=emptyList;
    }
}
