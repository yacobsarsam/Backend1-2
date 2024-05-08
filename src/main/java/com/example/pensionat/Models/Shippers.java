package com.example.pensionat.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shippers {

    @Id
    @GeneratedValue
    protected long id;
    @Column(nullable = false)
    protected String companyName;
    @Column(nullable = false)
    protected String phone;

    public Shippers(String companyName, String phone) {
        this.companyName = companyName;
        this.phone = phone;
    }

}

