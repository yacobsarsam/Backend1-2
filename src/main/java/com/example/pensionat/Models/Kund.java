package com.example.pensionat.Models;

import jakarta.persistence.*;
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
    @Column(nullable = false, unique = false, length = 100)
    protected String namn;
    @Column(nullable = false, unique = false)
    protected String tel;
    @Column(nullable = false, unique = true)
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
