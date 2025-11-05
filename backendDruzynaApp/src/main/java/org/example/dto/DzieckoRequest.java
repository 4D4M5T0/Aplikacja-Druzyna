package org.example.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DzieckoRequest {
    private String imie;
    private String nazwisko;
    private String dataUrodzenia;
    private String pesel;
    private Long kategoriaId;
}
