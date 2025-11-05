package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.dto.*;
import org.example.service.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/rejestracja")
    public ResponseEntity<AuthResponse> rejestracja(@RequestBody RejestracjaRequest request) {
        try {
            AuthResponse response = authService.rejestracja(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logowanie")
    public ResponseEntity<AuthResponse> logowanie(@RequestBody LogowanieRequest request) {
        try {
            AuthResponse response = authService.logowanie(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}