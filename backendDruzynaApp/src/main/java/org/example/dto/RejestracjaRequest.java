package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejestracjaRequest {
    private String email;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String telefon;
}
