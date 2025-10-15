package com.shea.ghostnets.domain;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;

/**
Aufgabenstellung: Es gibt meldende und bergende Personen.
 Beide Arten von Personen sind natürliche Personen mit einem Namen und einer Telefonnummer für Rückfragen.
 interpretation: Eine Person kann Melder, Berger oder Melder UND Berger sein -> HashSet (?)
 - id geben zusätzlich
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<PersonRole> roles = new HashSet<>();
}
