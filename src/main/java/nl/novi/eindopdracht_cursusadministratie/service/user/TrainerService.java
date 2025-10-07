package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final CertificateRepository certificateRepository;

    // ================================================================
    //  TRAINER INFORMATIE
    // ================================================================

    /** Eigen gegevens ophalen */
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    /** Eigen gegevens bijwerken */
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

    // ================================================================
    //  CURSUSSEN
    // ================================================================

    /** Alle cursussen van deze trainer ophalen */
    public List<Course> getCoursesByTrainer(Long trainerId) {
        return courseRepository.findByTrainerId(trainerId);
    }

    // ================================================================
    //  AANWEZIGHEID
    // ================================================================

    /** Aanwezigheid van cursisten registreren */
    public Registration markAttendance(Long registrationId, boolean aanwezig) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + registrationId));

        registration.setPresent(aanwezig);
        return registrationRepository.save(registration);
    }

    // ================================================================
    //  CERTIFICATEN
    // ================================================================

    /** Nieuw certificaat genereren */
    public Certificate generateCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    /** Alle certificaten van deze trainer ophalen */
    public List<Certificate> getCertificatesByTrainer(Long trainerId) {
        return certificateRepository.findByTrainerId(trainerId);
    }
}
