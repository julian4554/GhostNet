package com.shea.ghostnets.web;

import com.shea.ghostnets.domain.GhostNet;
import com.shea.ghostnets.service.GhostNetService;
import com.shea.ghostnets.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 REST-Controller  alle Anfragen geistrenetze
 sinn:
 Entgegennahme der HTTP-Request
 Validierung der Eingaben @Valid
 Aufruf der Logik im GhostNetService
 Rückgabe der Ergebnisse als Http
 Alle Routen beginnen mit /api/nets
 */
@RestController
@RequestMapping("/api/nets")
@RequiredArgsConstructor
public class GhostNetController {
    private final GhostNetService service;


    @PostMapping
    public ResponseEntity<GhostNet> create(@Valid @RequestBody CreateNetRequest req) {
        //nimm netzmeldung entgegen und leitet an service
        return ResponseEntity.ok(service.createNet(req));
    }

    @GetMapping("/available")
    // liefert alle netze die nicht geborgen worden sind
    public List<GhostNet> available() {
        return service.listAvailable(); }


    @PostMapping("/{id}/assign")
    public ResponseEntity<GhostNet> assign(@PathVariable("id") Long id, @Valid @RequestBody AssignSalvagerRequest req) {
        // wird aufgerufen wenn eine person die Bergung eines netzes nimmt
        return ResponseEntity.ok(service.assignSalvager(id, req));
    }


    @PostMapping("/{id}/recover")
public ResponseEntity<?> recover(@PathVariable("id") Long id, @Valid @RequestBody AssignSalvagerRequest req) {
        //meldet das netz als geborgen (nur bergende dürgen das melden)
        return ResponseEntity.ok(service.markRecovered(id, req));
    }


@PostMapping("/{id}/lost")
public ResponseEntity<?> markLost(@PathVariable("id") Long id, @Valid @RequestBody AssignSalvagerRequest reporterInfo) {

        //meldet netz als verschollen
        return ResponseEntity.ok(service.markLost(id, reporterInfo));

}}
