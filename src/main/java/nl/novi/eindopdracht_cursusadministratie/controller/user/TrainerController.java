package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.GenerateCertificateRequest;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.service.user.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor trainers.
 * Trainers kunnen hun eigen gegevens beheren, cursussen inzien,
 * aanwezigheid van cursisten registreren en certificaten genereren.
 */
@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    // ================================================================
    //  TRAINER INFO
    // ================================================================

    /** Eigen gegevens ophalen */
    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    /** Eigen gegevens bijwerken */
    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainer));
    }

    // ================================================================
    //  CURSUSSEN
    // ================================================================

    /** Alle cursussen van deze trainer ophalen */
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getCoursesByTrainer(id));
    }

    // ================================================================
    //  AANWEZIGHEID
    // ================================================================

    /**
     * Registreert de aanwezigheid van een cursist voor een specifieke inschrijving.
     * @param registrationId ID van de inschrijving
     * @param aanwezig true = aanwezig, false = afwezig
     */
    @PutMapping("/registrations/{registrationId}/attendance")
    public ResponseEntity<Registration> markAttendance(
            @PathVariable Long registrationId,
            @RequestParam boolean aanwezig) {
        return ResponseEntity.ok(trainerService.markAttendance(registrationId, aanwezig));
    }

    // ================================================================
    //  CERTIFICATEN
    // ================================================================

    /**
     * Genereert een nieuw certificaat voor een cursist.
     * Ontvangt alleen de minimale gegevens via GenerateCertificateRequest.
     */
    @PostMapping("/certificates")
    public ResponseEntity<Certificate> generateCertificate(@RequestBody GenerateCertificateRequest req) {
        Certificate createdCertificate = trainerService.generateCertificate(
                req.courseId(),
                req.studentId(),
                req.issuedBy()
        );
        return ResponseEntity.ok(createdCertificate);
    }

    /** Alle certificaten van deze trainer ophalen */
    @GetMapping("/{id}/certificates")
    public ResponseEntity<List<Certificate>> getCertificatesByTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getCertificatesByTrainer(id));
    }
}
