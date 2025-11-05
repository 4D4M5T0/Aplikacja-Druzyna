package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Table(name = "dzieci")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dziecko {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false)
    private String dataUrodzenia;

    @Column(nullable = false)
    private String pesel;

    @ManyToOne
    @JoinColumn(name = "rodzic_id", nullable = false)
    private Rodzic rodzic;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private KategoriaWiekowa kategoria;
}
