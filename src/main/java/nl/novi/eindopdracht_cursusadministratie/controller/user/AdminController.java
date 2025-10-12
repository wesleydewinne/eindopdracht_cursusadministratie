package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor alle beheeracties die alleen door een Admin uitgevoerd mogen worden.
 * - Gebruikersbeheer (Cursisten & Trainers)
 * - Cursusbeheer
 * - Inschrijvingen beheren
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('Admin')")
public class AdminController {

    private final AdminService adminService;

    // ============================================================
    // GEBRUIKERSBEHEER
    // ============================================================

    /** Alle gebruikers ophalen */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /** Gebruiker verwijderen */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // CURSISTEN
    // ============================================================

    /** Alle cursisten ophalen */
    @GetMapping("/cursisten")
    public ResponseEntity<List<? extends User>> getAllCursisten() {
        return ResponseEntity.ok(adminService.getAllCursisten());
    }

    /** Nieuwe cursist aanmaken */
    @PostMapping("/cursisten")
    public ResponseEntity<User> createCursist(@RequestBody User cursist) {
        User created = adminService.createCursist(cursist);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Bestaande cursist bijwerken */
    @PutMapping("/cursisten/{id}")
    public ResponseEntity<User> updateCursist(@PathVariable Long id, @RequestBody User updatedCursist) {
        User updated = adminService.updateCursist(id, updatedCursist);
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // TRAINERS
    // ============================================================

    /** Alle trainers ophalen */
    @GetMapping("/trainers")
    public ResponseEntity<List<? extends User>> getAllTrainers() {
        return ResponseEntity.ok(adminService.getAllTrainers());
    }

    /** Nieuwe trainer aanmaken */
    @PostMapping("/trainers")
    public ResponseEntity<User> createTrainer(@RequestBody User trainer) {
        User created = adminService.createTrainer(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Trainer bijwerken */
    @PutMapping("/trainers/{id}")
    public ResponseEntity<User> updateTrainer(@PathVariable Long id, @RequestBody User updatedTrainer) {
        User updated = adminService.updateTrainer(id, updatedTrainer);
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // CURSUSSEN
    // ============================================================

    /** Alle cursussen ophalen */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses());
    }

    /** Nieuwe cursus aanmaken */
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course created = adminService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Cursus bijwerken */
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Course updated = adminService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(updated);
    }

    /** Cursus verwijderen */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // REGISTRATIES
    // ============================================================

    /** Inschrijving verwijderen */
    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        adminService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
