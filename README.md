#  GhostNets – Sprint 1 Prototype

**GhostNets** ist eine Webanwendung zur Erfassung, Meldung und Bergung sogenannter *Geisternetze*  also verlorener oder treibender Fischernetze, die eine Gefahr für Meereslebewesen darstellen.  
Das Projekt wurde im Rahmen eines Hochschulprojekts entwickelt.

---

##  Tech Stack

- Java 21
- Spring Boot 3.3
- Spring Data JPA
- H2 Database (persistent / file-based)
- Maven
- HTML / CSS Frontend

---

##  Setup & Installation

###  Repository klonen
```bash
git clone https://github.com/julian4554/GhostNet.git
cd GhostNet
```

###  Projekt starten

Projekt mit Maven starten:
```bash
mvn spring-boot:run
```

Alternativ kann das Projekt direkt in **IntelliJ IDEA** ausgeführt werden.

---

##  Zugriff auf die Anwendung

Nach dem Start:

**Weboberfläche:**  
```bash
http://localhost:8080
```
**H2-Konsole:**
```bash
http://localhost:8080/h2-console
```
**JDBC-URL:**
```bash
jdbc:h2:file:./data/ghostnets;AUTO_SERVER=TRUE
```
```bash
Benutzer:sa 
Passwort: (leer)
```
---


