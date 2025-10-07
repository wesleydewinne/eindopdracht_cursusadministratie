package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;

    // Alle cursussen van een trainer ophalen
    public List<Course> getCoursesByTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + trainerId));
        return trainer.getCourses();
    }

    // Certificaten genereren voor cursisten die geslaagd zijn
    public Certificate generateCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    // Trainer details ophalen
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    // Trainer details bijwerken (optioneel)
    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {
        return trainerRepository.findById(id)
                .map(t -> {
                    t.setName(updatedTrainer.getName());
                    t.setEmail(updatedTrainer.getEmail());
                    t.setExpertise(updatedTrainer.getExpertise());
                    return trainerRepository.save(t);
                })
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }
}
