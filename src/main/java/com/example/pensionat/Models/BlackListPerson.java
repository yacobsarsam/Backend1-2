package com.example.pensionat.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlackListPerson {
    //@Id
    public int id;
    public String email;
    public String name;
    public String group;
    public Date created;
    public boolean ok;
}
