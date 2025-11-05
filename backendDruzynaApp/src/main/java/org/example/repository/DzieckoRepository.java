package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.*;
import java.util.Optional;
import java.util.List;

@Repository
public interface DzieckoRepository extends JpaRepository<Dziecko, Long> {
    List<Dziecko> findByRodzicId(Long rodzicId);
    List<Dziecko> findByKategoriaId(Long kategoriaId);
}
