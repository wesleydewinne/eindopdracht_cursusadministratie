package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor trainers.
 * Trainers kunnen hun gegevens beheren, cursussen inzien,
 * aanwezigheid registreren en certificaten genereren.
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

    /** Cursussen van deze trainer ophalen */
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getCoursesByTrainer(id));
    }

    // ================================================================
    //  AANWEZIGHEID
    // ================================================================

    /** Aanwezigheid van een cursist registreren */
    @PutMapping("/registrations/{registrationId}/attendance")
    public ResponseEntity<Registration> markAttendance(
            @PathVariable Long registrationId,
            @RequestParam boolean aanwezig) {
        return ResponseEntity.ok(trainerService.markAttendance(registrationId, aanwezig));
    }

    // ================================================================
    //  CERTIFICATEN
    // ================================================================

    /** Certificaat genereren */
    @PostMapping("/certificates")
    public ResponseEntity<Certificate> generateCertificate(@RequestBody Certificate certificate) {
        return ResponseEntity.ok(trainerService.generateCertificate(certificate));
    }

    /** Alle certificaten van deze trainer ophalen */
    @GetMapping("/{id}/certificates")
    public ResponseEntity<List<Certificate>> getCertificatesByTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getCertificatesByTrainer(id));
    }
}
