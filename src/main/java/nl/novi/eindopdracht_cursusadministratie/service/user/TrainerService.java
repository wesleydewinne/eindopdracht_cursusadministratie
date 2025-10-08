package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.helper.CertificateNumberHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.DateHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.MailHelper;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final MailHelper mailHelper;

    // ================================================================
    //  TRAINER INFORMATIE
    // ================================================================

    /** Haalt een specifieke trainer op basis van ID. */
    public Trainer getTrainerById(Long id) {
        return findEntityById(id, trainerRepository, new TrainerNotFoundException(id));
    }

    /** Werkt de gegevens van een trainer bij. */
    public Trainer updateTrainer(Long id, Trainer updatedTrainer) {
        Trainer existing = findEntityById(id, trainerRepository, new TrainerNotFoundException(id));
        existing.setName(updatedTrainer.getName());
        existing.setEmail(updatedTrainer.getEmail());
        existing.setExpertise(updatedTrainer.getExpertise());
        return trainerRepository.save(existing);
    }

    // ================================================================
    //  CURSUSSEN
    // ================================================================

    /**
     * Haalt alle cursussen op die aan deze trainer gekoppeld zijn.
     * Trainers kunnen alleen hun eigen cursussen bekijken.
     */
    public List<Course> getCoursesByTrainer(Long trainerId) {
        verifyTrainerOwnership(trainerId); //
        return courseRepository.findByTrainerId(trainerId);
    }

    // ================================================================
    //  AANWEZIGHEID
    // ================================================================

    /**
     * Registreert de aanwezigheid van een cursist.
     * Alleen toegestaan voor cursussen van de ingelogde trainer.
     */
    public Registration markAttendance(Long registrationId, boolean aanwezig) {
        Registration registration = findEntityById(registrationId, registrationRepository, new RegistrationNotFoundException(registrationId));

        //  Controleer of de ingelogde trainer eigenaar is van de cursus
        verifyTrainerOwnership(registration.getCourse().getTrainer().getId());

        registration.setPresent(aanwezig);
        return registrationRepository.save(registration);
    }

    // ================================================================
    //  CERTIFICATEN
    // ================================================================

    /**
     * Genereert een nieuw certificaat voor een cursist.
     * Alleen toegestaan voor de trainer van de betreffende cursus.
     */
    public Certificate generateCertificate(Long courseId, Long studentId, String issuedBy) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User student = findEntityById(studentId, userRepository, new CursistNotFoundException(studentId));

        //  Controleer of de ingelogde trainer bevoegd is voor deze cursus
        verifyTrainerOwnership(course.getTrainer().getId());

        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("No certificate is issued for evacuation drills.");
        }

        LocalDate today = LocalDate.now();

        Certificate cert = new Certificate();
        cert.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        cert.setIssueDate(today);
        cert.setExpiryDate(DateHelper.calculateExpiryDate(today, course.getType()));
        cert.setIssuedBy(issuedBy != null ? issuedBy : "BHV Training");
        cert.setStudent(student);
        cert.setCourse(course);

        Certificate savedCert = certificateRepository.save(cert);

        //  Automatische e-mailmelding naar cursist
        mailHelper.sendCertificateNotification(
                student.getEmail(),
                student.getName(),
                course.getName(),
                cert.getCertificateNumber(),
                cert.getExpiryDate().toString(),
                cert.getIssuedBy()
        );

        return savedCert;
    }

    /** Haalt alle certificaten op die gekoppeld zijn aan deze trainer. */
    public List<Certificate> getCertificatesByTrainer(Long trainerId) {
        verifyTrainerOwnership(trainerId);
        return certificateRepository.findByCourse_Trainer_Id(trainerId);
    }

    // ================================================================
    //  BEVEILIGINGSHULPMETHODEN
    // ================================================================

    /**
     * Controleert of de ingelogde trainer dezelfde is als de trainer
     * bij de opgevraagde cursus of resource.
     */
    private void verifyTrainerOwnership(Long trainerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Geen actieve sessie gevonden.");
        }

        User loggedInUser = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("Ingelogde gebruiker niet gevonden."));

        // Als trainer niet overeenkomt → geen toegang
        if (!loggedInUser.getId().equals(trainerId)) {
            throw new AccessDeniedException("Je bent niet bevoegd om deze cursus te beheren of bekijken.");
        }
    }
}
