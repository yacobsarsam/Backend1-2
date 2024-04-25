package com.example.pensionat.Dtos;

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
public class DetailedBokingDto {
    protected long id;
    protected String startdatum;
    protected String slutdatum;
    protected int numOfBeds;
}
