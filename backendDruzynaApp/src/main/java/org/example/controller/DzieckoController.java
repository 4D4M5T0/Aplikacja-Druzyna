package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Dziecko;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.dto.*;
import org.example.service.*;
import java.util.List;
@RestController
@RequestMapping("/api/dzieci")
@RequiredArgsConstructor
public class DzieckoController {

    private final DzieckoService dzieckoService;

    @GetMapping("/rodzic/{rodzicId}")
    public ResponseEntity<List<DzieckoResponse>> getDzieciRodzica(@PathVariable Long rodzicId,
                                                                  @RequestHeader("Authorization") String token) {
        List<DzieckoResponse> dzieci = dzieckoService.getDzieciRodzica(rodzicId);
        return ResponseEntity.ok(dzieci);
    }

    @PostMapping("/rodzic/{rodzicId}")
    public ResponseEntity<DzieckoResponse> dodajDziecko(@PathVariable Long rodzicId,
                                                        @RequestBody DzieckoRequest request,
                                                        @RequestHeader("Authorization") String token) {
        DzieckoResponse dziecko = dzieckoService.dodajDziecko(rodzicId, request);
        return ResponseEntity.ok(dziecko);
    }

    @DeleteMapping("/{dzieckoId}/rodzic/{rodzicId}")
    public ResponseEntity<Void> usunDziecko(@PathVariable Long dzieckoId,
                                            @PathVariable Long rodzicId,
                                            @RequestHeader("Authorization") String token) {
        dzieckoService.usunDziecko(dzieckoId, rodzicId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{dzieckoId}/kategoria/{kategoriaId}/rodzic/{rodzicId}")
    public ResponseEntity<DzieckoResponse> przypiszDoKategorii(@PathVariable Long dzieckoId,
                                                               @PathVariable Long kategoriaId,
                                                               @PathVariable Long rodzicId,
                                                               @RequestHeader("Authorization") String token) {
        DzieckoResponse dziecko = dzieckoService.przypiszDoKategorii(dzieckoId, kategoriaId, rodzicId);
        return ResponseEntity.ok(dziecko);
    }
    @GetMapping
    public List<Dziecko> getAllDzieci() {
        return dzieckoService.getAllDzieci();
    }

}

