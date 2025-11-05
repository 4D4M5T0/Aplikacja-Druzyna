package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Table(name = "kategorie_wiekowe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KategoriaWiekowa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nazwa; // np. "Orlik (2017-2018)"

    @Column(nullable = false)
    private int rokOd;

    @Column(nullable = false)
    private int rokDo;

    @Column
    private String opis;

    @OneToMany(mappedBy = "kategoria")
    private List<Dziecko> dzieci;
}
