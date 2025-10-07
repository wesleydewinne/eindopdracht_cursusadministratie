package nl.novi.eindopdracht_cursusadministratie.controller;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor alle beheerfunctionaliteiten.
 * De Admin kan gebruikers (trainers & cursisten) en cursussen beheren.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ================================================================
    //  GEBRUIKERSBEHEER
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
    //  cursisten
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
    //  Trainers
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
}
