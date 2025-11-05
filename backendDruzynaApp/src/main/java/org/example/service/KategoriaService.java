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
public class KategoriaService {

    private final KategoriaWiekowaRepository kategoriaRepo;

    public List<KategoriaResponse> getAllKategorie() {
        return kategoriaRepo.findAll().stream()
                .map(k -> new KategoriaResponse(k.getId(), k.getNazwa(),
                        k.getRokOd(), k.getRokDo(), k.getOpis()))
                .collect(Collectors.toList());
    }
}
