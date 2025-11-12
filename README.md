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

## 📝 Projectomschrijving

De **Cursusadministratie API** automatiseert het administratieve proces van trainingen zoals **BHV**, **EHBO** en **ontruimingsoefeningen**.  
De backend beheert cursisten, trainers, locaties, certificaten en ontruimingsverslagen via een beveiligde REST API.

- Trainers kunnen deelnemers beheren, aanwezigheden registreren en certificaten genereren.
- Cursisten kunnen hun eigen gegevens en certificaten inzien en certificaat-PDF’s downloaden.
- Beheerders hebben volledig overzicht over gebruikers, cursussen, locaties, certificaten en rapportages.

---

## ⚙️ Technische Stack

| Component           | Technologie                                   |
|---------------------|-----------------------------------------------|
| **Programmeertaal** | Java 21                                      |
| **Framework**       | Spring Boot 3                                |
| **Database**        | PostgreSQL                                   |
| **ORM**             | Spring Data JPA                              |
| **Beveiliging**     | Spring Security + JWT (rolgebaseerde auth)   |
| **Testing**         | JUnit 5, Mockito, Spring MockMvc             |
| **PDF-generatie**   | OpenPDF (LibrePDF)                           |
| **Build tool**      | Maven                                        |
| **Versiebeheer**    | Git / GitHub                                 |

---

## 🚀 Functionaliteiten

✅ **Cursusbeheer**
- Cursussen aanmaken, bijwerken en verwijderen (BHV, EHBO, ontruimingsoefeningen)
- Koppelen van trainers en locaties
- Specifieke logica per trainingstype (rapport wel/niet verplicht, max. deelnemers, geldigheidsduur certificaat)

✅ **Inschrijvingen (registrations)**
- Cursisten inschrijven voor cursussen
- Controle op dubbelinschrijvingen en maximale capaciteit
- Administratieve statuswijzigingen door admin (REGISTERED, PENDING, APPROVED, CANCELLED, ABSENT, COMPLETED)

✅ **Aanwezigheid & beoordeling**
- Trainers registreren aanwezigheid per cursist
- Beoordelen van aanwezige cursisten
- Automatisch markeren van afwezigen bij het afronden van een cursus

✅ **Certificaten**
- Automatische certificaatgeneratie (met nummer, uitgiftedatum, vervaldatum)
- PDF-certificaat aanmaken en opslaan in de database
- Overzicht verlopen en binnenkort verlopen certificaten

✅ **Ontruimingsverslagen**
- Rapportage voor ontruimingsoefeningen (verschillende fases)
- Automatisch evaluatie-advies o.b.v. fase, evacuatie-tijd en gebouwgrootte
- Goedkeuren/afkeuren door admin
- PDF-verslag genereren met rapportdetails

✅ **Beveiliging & infrastructuur**
- JWT-authenticatie (`/api/auth/login`)
- Rolgebaseerde autorisatie (ADMIN / TRAINER / CURSIST)
- Globale exception handling met nette JSON-responses
- Validatie met Jakarta Validation (Bean Validation)

---

## 👥 Gebruikersrollen

| Rol                | Beschrijving                                                                 |
|--------------------|-------------------------------------------------------------------------------|
| **Beheerder (ADMIN)**  | Beheert gebruikers, cursussen, certificaten, locaties en ontruimingsverslagen |
| **Trainer (TRAINER)**  | Beheert eigen cursussen, aanwezigheden, beoordelingen en certificaten       |
| **Cursist (CURSIST)**  | Kan eigen gegevens, inschrijvingen en certificaten inzien en PDF downloaden |

---

## 🧱 Projectstructuur

```plaintext
src/
 ├── main/
 │   ├── java/nl/novi/eindopdracht_cursusadministratie/
 │   │   ├── controller/
 │   │   │   ├── auth/              # /api/auth (login, token-check, current user)
 │   │   │   ├── certificate/       # /api/certificates (CRUD + PDF-download)
 │   │   │   ├── course/            # /api/courses (overzichten voor admin/trainer/cursist)
 │   │   │   ├── registration/      # /api/registrations (inschrijvingen)
 │   │   │   ├── report/            # /api/reports (ontruimingsverslagen + PDF)
 │   │   │   └── user/              # /api/admin, /api/trainers, /api/cursisten
 │   │   ├── dto/
 │   │   │   ├── auth/              # AuthenticationRequest, AuthenticationResponse, CurrentUserDto
 │   │   │   ├── certificate/       # CertificateResponseDto, GenerateCertificateRequest
 │   │   │   ├── course/            # Course*Dto's voor admin/trainer/cursist
 │   │   │   ├── location/          # Location*Dto's
 │   │   │   ├── registration/      # Registration*Dto's
 │   │   │   ├── report/            # EvacuationReport*Dto's
 │   │   │   ├── response/          # Generieke responses (bijv. DeleteResponseDto)
 │   │   │   └── user/              # Admin/Trainer/Cursist DTO's
 │   │   ├── exception/             # Custom exceptions + GlobalExceptionHandler
 │   │   ├── helper/                # Helpers (CertificateNumber, DateHelper, PdfHelper, Security-helpers, etc.)
 │   │   ├── model/
 │   │   │   ├── certificate/       # Certificate entity
 │   │   │   ├── course/            # Course, TrainingType
 │   │   │   ├── location/          # Location
 │   │   │   ├── registration/      # Registration, RegistrationStatus
 │   │   │   ├── report/            # EvacuationReport, EvacuationPhase, ReportStatus
 │   │   │   └── user/              # User, Trainer, Cursist, Role
 │   │   ├── repository/
 │   │   │   ├── certificate/       # CertificateRepository
 │   │   │   ├── course/            # CourseRepository
 │   │   │   ├── location/          # LocationRepository
 │   │   │   ├── registration/      # RegistrationRepository
 │   │   │   ├── report/            # EvacuationReportRepository
 │   │   │   └── user/              # UserRepository, TrainerRepository, CursistRepository
 │   │   ├── security/
 │   │   │   ├── filter/            # JwtRequestFilter
 │   │   │   ├── CustomUserDetails  # UserDetails-implementatie
 │   │   │   ├── CustomUserDetailsService
 │   │   │   ├── JwtUtil
 │   │   │   └── SecurityConfig + GlobalCorsConfiguration
 │   │   └── service/
 │   │       ├── certificate/       # CertificateService
 │   │       ├── course/            # CourseService
 │   │       ├── location/          # LocationService
 │   │       ├── pdf/               # PdfCertificateService, PdfEvacuationReportService
 │   │       ├── registration/      # RegistrationService
 │   │       ├── report/            # EvacuationReportService
 │   │       └── user/              # AdminService, TrainerService, CursistService
 │   └── resources/
 │       ├── application.properties
 │       └── data.sql               # (optioneel) testdata / seed-data
 └── test/
     └── java/nl/novi/eindopdracht_cursusadministratie/
         ├── service/
         │   ├── CertificateServiceTest.java
         │   ├── CourseServiceTest.java
         │   └── TrainerServiceTest.java
         ├── controller/
         │   ├── CourseControllerIntegrationTest.java
         │   └── CertificateControllerIntegrationTest.java
         └── EindopdrachtCursusadministratieApplicationTests.java

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
    - `CourseControllerTest`
    - `CertificateControllerTest`
- Testen authenticatie, autorisatie en endpoint-output

**Tests uitvoeren:**
```bash
   mvn clean test
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
