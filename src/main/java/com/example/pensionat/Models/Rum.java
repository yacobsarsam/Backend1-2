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
    @Column(nullable = false)
    protected int price;

    @OneToMany(mappedBy = "rum")
    protected List<RumEvent> roomEvent; //gick inte att använda EventBase här (Borde inte EventBase och RumEvent vara samma klass?)
    public Rum(boolean dubbelrum, int storlek, int rumsnr, int price) {
        this.rumsnr = rumsnr;
        this.dubbelrum = dubbelrum;
        this.storlek = storlek;
        this.price = price;
    }

}
