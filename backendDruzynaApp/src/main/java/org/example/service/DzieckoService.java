package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.dto.*;
import org.example.model.*;
import org.example.repository.*;
import org.example.security.JwtUtil;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class DzieckoService {

    private final DzieckoRepository dzieckoRepo;
    private final RodzicRepository rodzicRepo;
    private final KategoriaWiekowaRepository kategoriaRepo;

    public List<DzieckoResponse> getDzieciRodzica(Long rodzicId) {
        List<Dziecko> dzieci = dzieckoRepo.findByRodzicId(rodzicId);
        return dzieci.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DzieckoResponse dodajDziecko(Long rodzicId, DzieckoRequest request) {
        Rodzic rodzic = rodzicRepo.findById(rodzicId)
                .orElseThrow(() -> new RuntimeException("Rodzic nie znaleziony"));

        Dziecko dziecko = new Dziecko();
        dziecko.setImie(request.getImie());
        dziecko.setNazwisko(request.getNazwisko());
        dziecko.setDataUrodzenia(request.getDataUrodzenia());
        dziecko.setPesel(request.getPesel());
        dziecko.setRodzic(rodzic);

        if (request.getKategoriaId() != null) {
            KategoriaWiekowa kategoria = kategoriaRepo.findById(request.getKategoriaId())
                    .orElseThrow(() -> new RuntimeException("Kategoria nie znaleziona"));
            dziecko.setKategoria(kategoria);
        }

        dziecko = dzieckoRepo.save(dziecko);
        return mapToResponse(dziecko);
    }

    public void usunDziecko(Long dzieckoId, Long rodzicId) {
        Dziecko dziecko = dzieckoRepo.findById(dzieckoId)
                .orElseThrow(() -> new RuntimeException("Dziecko nie znalezione"));

        if (!dziecko.getRodzic().getId().equals(rodzicId)) {
            throw new RuntimeException("Brak uprawnień");
        }

        dzieckoRepo.delete(dziecko);
    }

    public DzieckoResponse przypiszDoKategorii(Long dzieckoId, Long kategoriaId, Long rodzicId) {
        Dziecko dziecko = dzieckoRepo.findById(dzieckoId)
                .orElseThrow(() -> new RuntimeException("Dziecko nie znalezione"));

        if (!dziecko.getRodzic().getId().equals(rodzicId)) {
            throw new RuntimeException("Brak uprawnień");
        }

        KategoriaWiekowa kategoria = kategoriaRepo.findById(kategoriaId)
                .orElseThrow(() -> new RuntimeException("Kategoria nie znaleziona"));

        dziecko.setKategoria(kategoria);
        dziecko = dzieckoRepo.save(dziecko);

        return mapToResponse(dziecko);
    }

    private DzieckoResponse mapToResponse(Dziecko dziecko) {
        return new DzieckoResponse(
                dziecko.getId(),
                dziecko.getImie(),
                dziecko.getNazwisko(),
                dziecko.getDataUrodzenia(),
                dziecko.getPesel(),
                dziecko.getKategoria() != null ? dziecko.getKategoria().getId() : null,
                dziecko.getKategoria() != null ? dziecko.getKategoria().getNazwa() : null
        );
    }
    public List<Dziecko> getAllDzieci() {
        return dzieckoRepo.findAll();
    }

}
