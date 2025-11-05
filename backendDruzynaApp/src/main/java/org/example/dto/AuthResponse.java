package org.example.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long rodzicId;
    private String email;
    private String imie;
    private String nazwisko;
}
