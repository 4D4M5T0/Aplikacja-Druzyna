package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "rodzice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rodzic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String haslo;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column
    private String telefon;

    @OneToMany(mappedBy = "rodzic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dziecko> dzieci;
}
