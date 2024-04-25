package com.example.pensionat.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//Har skapar Dto-klassar, vi kan ändra innehåller och lägga
// till fler droklasser om det behövs utifrån vårt behov
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedKundDto {
    private long id;
    private String namn;
    private String tel;
    private String email;
    List<BokingDto> bokingDtos;
}
