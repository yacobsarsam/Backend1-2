package com.example.pensionat.Pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//Har skapar Dto-klassar, vi kan ändra innehåller och lägga
// till fler droklasser om det behövs utifrån vårt behov
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KundDto {
    private long id;
    private String namn;
    private String tel;
}
