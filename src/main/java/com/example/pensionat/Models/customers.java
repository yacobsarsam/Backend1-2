package com.example.pensionat.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class customers {
    public int id;
    public String companyName;
    public String contactName;
    public String contactTitle;
    public String streetAddress;
    public String city;
    public int postalCode;
    public String country;
    public String phone;
    public String fax;
}
