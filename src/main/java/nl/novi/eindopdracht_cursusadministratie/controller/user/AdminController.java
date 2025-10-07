package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.user.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cursisten")
    public ResponseEntity<List<User>> getAllCursisten() {
        return ResponseEntity.ok(adminService.getAllCursisten());
    }

    @PostMapping("/cursisten")
    public ResponseEntity<User> createCursist(@RequestBody User cursist) {
        return ResponseEntity.ok(adminService.createCursist(cursist));
    }

    @PutMapping("/cursisten/{id}")
    public ResponseEntity<User> updateCursist(@PathVariable Long id, @RequestBody User updatedCursist) {
        return ResponseEntity.ok(adminService.updateCursist(id, updatedCursist));
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<User>> getAllTrainers() {
        return ResponseEntity.ok(adminService.getAllTrainers());
    }

    @PostMapping("/trainers")
    public ResponseEntity<User> createTrainer(@RequestBody User trainer) {
        return ResponseEntity.ok(adminService.createTrainer(trainer));
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<User> updateTrainer(@PathVariable Long id, @RequestBody User updatedTrainer) {
        return ResponseEntity.ok(adminService.updateTrainer(id, updatedTrainer));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses());
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(adminService.createCourse(course));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(adminService.updateCourse(id, updatedCourse));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        adminService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
