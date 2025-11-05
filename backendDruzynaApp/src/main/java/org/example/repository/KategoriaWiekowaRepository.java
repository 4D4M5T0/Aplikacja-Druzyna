package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.KategoriaWiekowa;

import java.util.Optional;

@Repository
public interface KategoriaWiekowaRepository extends JpaRepository<KategoriaWiekowa, Long> {
    Optional<KategoriaWiekowa> findByNazwa(String nazwa);
}
