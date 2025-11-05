package org.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.model.Rodzic;

import java.util.Optional;

@Repository
public interface RodzicRepository extends JpaRepository<Rodzic, Long> {
    boolean existsByEmail(String email);
    Optional<Rodzic> findByEmail(String email);
}
