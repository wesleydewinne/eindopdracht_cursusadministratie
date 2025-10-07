package nl.novi.eindopdracht_cursusadministratie.controller;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    //  Cursussen van deze trainer
    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getCoursesByTrainer(id));
    }

    //  Certificaat genereren
    @PostMapping("/certificates")
    public ResponseEntity<Certificate> generateCertificate(@RequestBody Certificate certificate) {
        return ResponseEntity.ok(trainerService.generateCertificate(certificate));
    }

    //  Eigen gegevens bekijken
    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    //  Eigen gegevens bijwerken
    @PutMapping("/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainer));
    }
}
