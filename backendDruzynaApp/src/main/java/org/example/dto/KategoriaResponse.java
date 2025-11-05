package org.example.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KategoriaResponse {
    private Long id;
    private String nazwa;
    private int rokOd;
    private int rokDo;
    private String opis;
}
