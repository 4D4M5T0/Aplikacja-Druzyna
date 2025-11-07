package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.dto.*;
import org.example.service.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Dodatkowe CORS na poziomie kontrolera
public class AuthController {

    private final AuthService authService;

    @PostMapping("/rejestracja")
    public ResponseEntity<?> rejestracja(@RequestBody RejestracjaRequest request) {
        try {
            System.out.println("=== OTRZYMANO REJESTRACJĘ ===");
            System.out.println("Email: " + request.getEmail());
            System.out.println("Imię: " + request.getImie());
            System.out.println("============================");

            AuthResponse response = authService.rejestracja(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Błąd rejestracji: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Błąd: " + e.getMessage());
        }
    }

    @PostMapping("/logowanie")
    public ResponseEntity<?> logowanie(@RequestBody LogowanieRequest request) {
        try {
            System.out.println("=== OTRZYMANO LOGOWANIE ===");
            System.out.println("Email: " + request.getEmail());
            System.out.println("===========================");

            AuthResponse response = authService.logowanie(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Błąd logowania: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Błąd: " + e.getMessage());
        }
    }
}