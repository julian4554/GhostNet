package com.shea.ghostnets.repo;

import com.shea.ghostnets.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * spring data jpa repo für die Verwaltung und Abfrage der Datenbank
 * ermöglicht Zugriff auf Geisternetze, abfrage nach ihrem Status etc
 */
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
    List<GhostNet> findByStatus(NetStatus status);
    @Query("SELECT n FROM GhostNet n WHERE n.status <> 'RESCUED'")  //alle Geisternetze anzeigen außer geborgene
    List<GhostNet> findAvailableForSalvage();
}
