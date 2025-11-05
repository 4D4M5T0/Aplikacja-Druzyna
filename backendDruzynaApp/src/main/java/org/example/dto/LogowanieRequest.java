package org.example.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogowanieRequest {
    private String email;
    private String haslo;
}
