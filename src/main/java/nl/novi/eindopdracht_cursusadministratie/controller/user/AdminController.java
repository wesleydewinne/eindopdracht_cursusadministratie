package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.AdminService;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor alle beheerfunctionaliteiten.
 * De Admin heeft toegang tot het beheren van gebruikers (trainers & cursisten),
 * cursussen en inschrijvingen.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ================================================================
    //  GEBRUIKERSBEHEER (ALGEMEEN)
    // ================================================================

    /** Alle gebruikers ophalen (ongeacht rol) */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /** Gebruiker verwijderen (trainer of cursist) */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ================================================================
    //  CURSISTENBEHEER
    // ================================================================

    /** Alle cursisten ophalen */
    @GetMapping("/cursisten")
    public ResponseEntity<List<User>> getAllCursisten() {
        return ResponseEntity.ok(adminService.getAllCursisten());
    }

    /** Nieuwe cursist aanmaken */
    @PostMapping("/cursisten")
    public ResponseEntity<User> createCursist(@RequestBody User cursist) {
        return ResponseEntity.ok(adminService.createCursist(cursist));
    }

    /** Cursist bijwerken */
    @PutMapping("/cursisten/{id}")
    public ResponseEntity<User> updateCursist(@PathVariable Long id, @RequestBody User updatedCursist) {
        return ResponseEntity.ok(adminService.updateCursist(id, updatedCursist));
    }

    // ================================================================
    //  TRAINERSBEHEER
    // ================================================================

    /** Alle trainers ophalen */
    @GetMapping("/trainers")
    public ResponseEntity<List<User>> getAllTrainers() {
        return ResponseEntity.ok(adminService.getAllTrainers());
    }

    /** Nieuwe trainer aanmaken */
    @PostMapping("/trainers")
    public ResponseEntity<User> createTrainer(@RequestBody User trainer) {
        return ResponseEntity.ok(adminService.createTrainer(trainer));
    }

    /** Trainer bijwerken */
    @PutMapping("/trainers/{id}")
    public ResponseEntity<User> updateTrainer(@PathVariable Long id, @RequestBody User updatedTrainer) {
        return ResponseEntity.ok(adminService.updateTrainer(id, updatedTrainer));
    }

    // ================================================================
    //  CURSUSBEHEER
    // ================================================================

    /** Alle cursussen ophalen */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses());
    }

    /** Nieuwe cursus aanmaken */
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(adminService.createCourse(course));
    }

    /** Bestaande cursus bijwerken (locatie, trainer, data, etc.) */
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(adminService.updateCourse(id, updatedCourse));
    }

    /** Cursus verwijderen */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // ================================================================
    //  INSCHRIJVINGSBEHEER
    // ================================================================

    /** Inschrijving verwijderen (door Admin) */
    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        adminService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
    // ================================================================
    //  ONTRUIMINGSVERSLAGEN
    // ================================================================

    @GetMapping("/evacuation-reports")
    public ResponseEntity<List<EvacuationReport>> getAllEvacuationReports() {
        return ResponseEntity.ok(adminService.getAllEvacuationReports());
    }

    @PutMapping("/evacuation-reports/{id}/approve")
    public ResponseEntity<EvacuationReport> approveEvacuationReport(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveEvacuationReport(id));
    }

    @PutMapping("/evacuation-reports/{id}/reject")
    public ResponseEntity<EvacuationReport> rejectEvacuationReport(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.rejectEvacuationReport(id));
    }

    @DeleteMapping("/evacuation-reports/{id}")
    public ResponseEntity<Void> deleteEvacuationReport(@PathVariable Long id) {
        adminService.deleteEvacuationReport(id);
        return ResponseEntity.noContent().build();
    }
}
