package com.shea.ghostnets.service;

import com.shea.ghostnets.domain.*;
import com.shea.ghostnets.repo.*;
import com.shea.ghostnets.web.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * GhostNetService
 * Enthält die zentrale Logik für alle Operationen rund um Geisternetze.
 * sinn:
 +  kommunikation mit frontend index.html
 *  zuordnung salvenger Personen
 *  markierung von Netzen als geborgen oder verschollen
 *  kommunikation mit den Repos
 *   transaktional, änderungen werden automatisch mit der Datenbank synchronisiert (in der theorie, workign on it)
 */
@Service
@RequiredArgsConstructor
@Transactional
public class GhostNetService {
    private final GhostNetRepository nets;
    private final PersonRepository persons;

    private static final Logger log = LoggerFactory.getLogger(GhostNetService.class);

    // erstellung eines Netzes wenn ein Netz gemeldet wird (ausgelöst durch Frontend knopf "Melden")
    public GhostNet createNet(CreateNetRequest req) {

        Person reporter = null;
        if (req.getReporterPhone() != null && !req.getReporterPhone().isBlank()) {
            reporter = persons.findByPhone(req.getReporterPhone())
                    .map(existing -> {
                        existing.getRoles().add(PersonRole.REPORTER);
                        // Name updaten, falls leer oder neu, keine doppelten
                        if ((existing.getName() == null || existing.getName().isBlank())
                                && req.getReporterName() != null && !req.getReporterName().isBlank()) {
                            existing.setName(req.getReporterName());
                        }
                        return persons.save(existing);
                    })
                    .orElseGet(() -> persons.save(Person.builder()
                            .name(req.getReporterName())
                            .phone(req.getReporterPhone())
                            .roles(Set.of(PersonRole.REPORTER))
                            .build()));
        }

        GhostNet net = GhostNet.builder()
                    .latitude(req.getLatitude())
                    .longitude(req.getLongitude())
                    .estimatedSize(req.getEstimatedSize())
                    .status(NetStatus.REPORTED)
                    .reporter(reporter)
                    .build();
        net = nets.save(net); // weil neues Objekt @transactional hat nicht funktioniert alleine
        log.info("Netz erfolgreich angelegt mit ID={} (Status={})", net.getId(), net.getStatus());
        return net;
    }




    public List<GhostNet> listAvailable() {

        return nets.findAvailableForSalvage();
    }

    // bergung übernehmen ausgelöst durch (frontend button "Übernehmen")
    public GhostNet assignSalvager(Long netId, AssignSalvagerRequest req) {
        GhostNet net = nets.findById(netId).orElseThrow(() -> new IllegalArgumentException("Netz mit ID " + netId + " existiert nicht"));
        if (net.getSalvager() != null) throw new IllegalStateException("Bereits vergeben");
        if (net.getStatus() != NetStatus.REPORTED && net.getStatus() != NetStatus.LOST)
            throw new IllegalStateException("Nur gemeldete oder verschollene Netze können übernommen werden.");


        // Prüfe, ob Person mit dieser Telefonnummer existiert
        Person salvager = persons.findByPhone(req.getPhone())
                .map(existing -> {
                    existing.getRoles().add(PersonRole.SALVAGER);
                    return persons.save(existing);
                })
                .orElseGet(() -> persons.save(Person.builder()
                        .name(req.getName())
                        .phone(req.getPhone())
                        .roles(Set.of(PersonRole.SALVAGER))
                        .build()));

        // Jetzt kann dieselbe Person mehrere Netze übernehmen
        net.setSalvager(salvager);
        net.setStatus(NetStatus.SALVAGE_PLANNED);
        log.info("Netz {} wurde {} zugewiesen (Status={})", net.getId(), req.getName(), net.getStatus());
        return net;
    }



       // Netz als geborgen melden
    public GhostNet markRecovered(Long netId, AssignSalvagerRequest req) {
        GhostNet net = nets.findById(netId).orElseThrow();
if (net.getSalvager() == null) {
    throw new IllegalStateException("Kein Bergender registriert – nur bergende Personen können geborgen melden.");
}
if (req == null || req.getName() == null || req.getName().isBlank() ||
    req.getPhone() == null || req.getPhone().isBlank()) {
    throw new IllegalArgumentException("Name und Telefonnummer der bergenden Person sind erforderlich.");
}
//Name einfacher String vergleich
if (!net.getSalvager().getName().equalsIgnoreCase(req.getName())) {
    throw new IllegalArgumentException("Nur die registrierte bergende Person darf geborgen melden.");
}
net.setStatus(NetStatus.RESCUED);
log.info(" Netz {} wurde als geborgen markiert ", net.getId());
return net;
    }

  // netz als verschollen melden
    public GhostNet markLost(Long netId, AssignSalvagerRequest reporterInfo) {
        GhostNet net = nets.findById(netId).orElseThrow();

        if (reporterInfo == null ||
                reporterInfo.getName() == null || reporterInfo.getName().isBlank() ||
                reporterInfo.getPhone() == null || reporterInfo.getPhone().isBlank()) {
            throw new IllegalArgumentException("Verschollen melden erfordert Name und Telefonnummer.");
        }

        // Prüfen, ob Person mit dieser Telefonnummer schon existiert, sonst neu anlegen wenn nihct
        Person reporter = persons.findByPhone(reporterInfo.getPhone())
                .map(existing -> {
                    existing.getRoles().add(PersonRole.REPORTER);
                    return persons.save(existing);
                })
                .orElseGet(() -> persons.save(Person.builder()
                        .name(reporterInfo.getName())
                        .phone(reporterInfo.getPhone())
                        .roles(Set.of(PersonRole.REPORTER))
                        .build()));

        net.setStatus(NetStatus.LOST);
        net.setReporter(reporter); // dokumentiert, wer verschollen gemeldet hat
        log.info(" Netz {} wurde als verschollen markiert", net.getId());
        return net;
    }}
