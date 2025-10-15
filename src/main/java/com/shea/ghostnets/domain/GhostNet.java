package com.shea.ghostnets.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 Aufgabenstellung:
 Ein Geisternetz hat die folgenden Eigenschaften:
 − Standort (GPS-Koordinaten) -> long lat?
 − geschätzte Größe und -> annahme m²?
 − Status ->  in NetStatus aufgeteilt
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GhostNet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //autogeneriert ids
    private double latitude; // koordis kommend vom Frontend
    private double longitude; // same
    private String estimatedSize;
    @Enumerated(EnumType.STRING)
    private NetStatus status; // .NetStatus
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // manytoone weil mehrere geisternetze von einer Person meldbar
    @JoinColumn(name = "reporter_id")
    private Person reporter;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // manytoone weil eine bergende Person mehere Netze sammeln kann
    @JoinColumn(name = "salvager_id")
    private Person salvager;
}
