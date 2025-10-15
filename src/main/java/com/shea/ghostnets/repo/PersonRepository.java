package com.shea.ghostnets.repo;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.shea.ghostnets.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * Repository für Person objekt
 * Stellt Datenbankzugriff
 * Übernimmt Standard-CRUD-Operationen automatisch (Spring Data JPA)
 * und erweitert diese um eine einfache Suchmethode nach Telefonnummer
 * Wird vom Service genutzt, um Meldende und Bergende Personen
 * anhand ihrer Telefonnummer eindeutig zu identifizieren.
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByPhone(String phone);
}
