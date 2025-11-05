package org.example.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DzieckoResponse {
    private Long id;
    private String imie;
    private String nazwisko;
    private String dataUrodzenia;
    private String pesel;
    private Long kategoriaId;
    private String kategoriaNazwa;
}