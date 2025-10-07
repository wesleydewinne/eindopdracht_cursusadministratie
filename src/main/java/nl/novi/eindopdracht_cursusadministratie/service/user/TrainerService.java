package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.helper.CertificateNumberHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.DateHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;

    // TRAINER INFO
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

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

    // CURSUSSEN
    public List<Course> getCoursesByTrainer(Long trainerId) {
        return courseRepository.findByTrainerId(trainerId);
    }

    // AANWEZIGHEID
    public Registration markAttendance(Long registrationId, boolean aanwezig) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + registrationId));
        registration.setPresent(aanwezig);
        return registrationRepository.save(registration);
    }

    // CERTIFICATEN
    public Certificate generateCertificate(Long courseId, Long studentId, String issuedBy) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalArgumentException("No certificate is issued for evacuation drills.");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        LocalDate today = LocalDate.now();
        Certificate cert = new Certificate();
        cert.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        cert.setIssueDate(today);
        cert.setExpiryDate(DateHelper.calculateExpiryDate(today, course.getType()));
        cert.setIssuedBy(issuedBy != null ? issuedBy : "Safety First BV");
        cert.setStudent(student);
        cert.setCourse(course);

        return certificateRepository.save(cert);
    }

    public List<Certificate> getCertificatesByTrainer(Long trainerId) {
        return certificateRepository.findByCourse_Trainer_Id(trainerId);
    }
}
