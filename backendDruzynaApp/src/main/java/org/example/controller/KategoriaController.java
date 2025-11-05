package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.dto.*;
import org.example.service.*;
import java.util.List;
@RestController
@RequestMapping("/api/kategorie")
@RequiredArgsConstructor
public class KategoriaController {

    private final KategoriaService kategoriaService;

    @GetMapping
    public ResponseEntity<List<KategoriaResponse>> getAllKategorie() {
        List<KategoriaResponse> kategorie = kategoriaService.getAllKategorie();
        return ResponseEntity.ok(kategorie);
    }
}
