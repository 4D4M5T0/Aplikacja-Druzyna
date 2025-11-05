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
public class AuthService {

    private final RodzicRepository rodzicRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse rejestracja(RejestracjaRequest request) {
        if (rodzicRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email już istnieje");
        }

        Rodzic rodzic = new Rodzic();
        rodzic.setEmail(request.getEmail());
        rodzic.setHaslo(passwordEncoder.encode(request.getHaslo()));
        rodzic.setImie(request.getImie());
        rodzic.setNazwisko(request.getNazwisko());
        rodzic.setTelefon(request.getTelefon());

        rodzic = rodzicRepo.save(rodzic);

        String token = jwtUtil.generateToken(rodzic.getEmail(), rodzic.getId());

        return new AuthResponse(token, rodzic.getId(), rodzic.getEmail(),
                rodzic.getImie(), rodzic.getNazwisko());
    }

    public AuthResponse logowanie(LogowanieRequest request) {
        Rodzic rodzic = rodzicRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nieprawidłowy email lub hasło"));

        if (!passwordEncoder.matches(request.getHaslo(), rodzic.getHaslo())) {
            throw new RuntimeException("Nieprawidłowy email lub hasło");
        }

        String token = jwtUtil.generateToken(rodzic.getEmail(), rodzic.getId());

        return new AuthResponse(token, rodzic.getId(), rodzic.getEmail(),
                rodzic.getImie(), rodzic.getNazwisko());
    }
}
