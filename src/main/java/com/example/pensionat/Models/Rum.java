package com.example.pensionat.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Rum {

    @Id
    @GeneratedValue
    protected long id;
    @Column(nullable = false)
    protected int rumsnr;
    @Column(nullable = false)
    protected boolean dubbelrum;
    @Column(nullable = false)
    protected int storlek;

    public Rum(boolean dubbelrum, int storlek, int rumsnr) {
        this.rumsnr = rumsnr;
        this.dubbelrum = dubbelrum;
        this.storlek = storlek;
    }

}
