# 🧑‍🏫 Cursusadministratie – Backend (Spring Boot)

Dit project is ontwikkeld als onderdeel van de **Eindopdracht Backend – Leerlijn Software Development**.  
De applicatie ondersteunt het volledige proces rondom **BHV-, EHBO- en ontruimingstrainingen**, van inschrijving tot certificering.

---

## 📋 Inhoudsopgave
1. [Projectomschrijving](#projectomschrijving)
2. [Technische Stack](#technische-stack)
3. [Functionaliteiten](#functionaliteiten)
4. [Gebruikersrollen](#gebruikersrollen)
5. [Projectstructuur](#projectstructuur)
6. [Testen](#testen)
7. [Installatie-instructies](#installatie-instructies)
8. [API-documentatie](#api-documentatie)
9. [Toekomstige uitbreidingen](#toekomstige-uitbreidingen)
10. [Auteur](#auteur)

---

## Projectomschrijving

De **Cursusadministratie API** automatiseert het administratieve proces van trainingen zoals **BHV**, **EHBO** en **ontruimingsoefeningen**.  
De backend beheert cursisten, trainers, locaties, certificaten en ontruimingsverslagen via een beveiligde REST API.

Trainers kunnen deelnemers beheren en certificaten genereren.  
Cursisten kunnen hun behaalde certificaten downloaden, en beheerders houden overzicht over alle trainingen.

---

## ⚙️ Technische Stack

| Component | Technologie                                 |
|------------|---------------------------------------------|
| **Programmeertaal** | Java 21                                     |
| **Framework** | Spring Boot 3                               |
| **Database** | PostgreSQL                                  |
| **ORM** | Spring Data JPA                             |
| **Beveiliging** | Spring Security (rolgebaseerde autorisatie) |
| **Testing** | JUnit 5, Mockito, MockMvc                   |
| **PDF-generatie** | OpenPDF (LibrePDF)                          |
| **Build tool** | Maven                                       |
| **Versiebeheer** | Git / GitHub                                |

---

## 🚀 Functionaliteiten

✅ Cursusbeheer (aanmaken, bijwerken, verwijderen)  
✅ Inschrijving van cursisten  
✅ Aanwezigheidsregistratie door trainers  
✅ Automatische PDF-certificaten  
✅ Uploaden van ontruimingsverslagen  
✅ Rolgebaseerde beveiliging (Admin / Trainer / Cursist)  
✅ Exception-handling & validatie  
✅ Unit- en integratietests met volledige dekking

---

## 👥 Gebruikersrollen

| Rol | Beschrijving |
|-----|---------------|
| **Beheerder (ADMIN)** | Beheert gebruikers, cursussen, certificaten en locaties |
| **Trainer (TRAINER)** | Houdt aanwezigheden bij, genereert certificaten, uploadt verslagen |
| **Cursist (CURSIST)** | Schrijft zich in voor cursussen en downloadt certificaten |

---

## 🧱 Projectstructuur

```plaintext
src/
 ├── main/
 │   ├── java/nl/novi/eindopdracht_cursusadministratie/
 │   │   ├── model/           # Entiteiten
 │   │   ├── repository/      # Spring Data repositories
 │   │   ├── service/         # Businesslogica
 │   │   ├── controller/      # REST endpoints
 │   │   └── helper/          # Hulpfuncties en utils
 │   └── resources/
 │       ├── application.properties
 │       └── data.sql         # Testdata
 └── test/
     ├── java/.../service/    # Unit-tests
     └── java/.../controller/ # Integratietests
```

---

##   Testen

Er zijn zowel **unit-tests** als **integratietests** geïmplementeerd.

### Unit-tests
- Getest met **JUnit 5** en **Mockito**
- 3 services volledig gedekt:
    - `CourseServiceTest`
    - `CertificateServiceTest`
    - `TrainerServiceTest`
- > 100% line coverage op de service-laag
- Duidelijke *Arrange → Act → Assert* structuur

### Integratietests
- 2 controller-tests met **MockMvc**
    - `CourseControllerIntegrationTest`
    - `CertificateControllerIntegrationTest`
- Testen authenticatie, autorisatie en endpoint-output

**Tests uitvoeren:**
```bash
mvn test
```

---

## 💻 Installatie-instructies

1. **Clone het project:**
   ```bash
   git clone https://github.com/wesleydewinne/eindopdracht_cursusadministratie.git
   ```

2. **Open het project in IntelliJ IDEA**  
   Zorg dat Maven dependencies automatisch worden gedownload.

3. **Database configuratie (PostgreSQL):**
    - Maak een database aan met de naam `eindopdracht_cursusadministratie`
    - Pas eventueel `spring.datasource.username` en `spring.datasource.password` aan in `application.properties`.

4. **Start de applicatie:**
   ```bash
   mvn spring-boot:run
   ```

5. **Test de API via Postman of browser:**
    - http://localhost:8080/api/courses
    - http://localhost:8080/api/certificates

---

##  API-documentatie

De API volgt REST-principes.  
Voorbeeld endpoints:

| Endpoint | Methode | Beschrijving | Rol |
|-----------|----------|---------------|-----|
| `/api/courses` | GET | Alle cursussen ophalen | Admin / Trainer |
| `/api/courses/{id}` | GET | Cursusdetails ophalen | Admin / Trainer |
| `/api/certificates/{id}` | GET | Certificaat downloaden | Admin / Cursist |
| `/api/trainers/registrations/{id}/attendance` | PUT | Aanwezigheid registreren | Trainer |

---

## 🔮 Toekomstige uitbreidingen

- Betalingssysteem (Mollie/iDEAL)
- Automatische facturatie
- Meertalige ondersteuning (i18n)
- E-mail- en pushnotificaties via `MailHelper`
- Dashboard voor beheerders
- Integratie met HRM-systemen (AFAS/Nmbrs)
- QR-code incheck via mobiele app

---

## 👤 Auteur

**Wesley De Winne**  
Backend Developer – 2025  
📧 [wesleydewinne@gmail.com](mailto:wesleydewinne@gmail.com)  
📘 [GitHub Repository](https://github.com/wesleydewinne/eindopdracht_cursusadministratie)

---

> ✅ Eindopdracht ingeleverd – versie 1.0  
> 🧪 Unit-tests: 100% line coverage  
> 🧩 Laatste update: oktober 2025
