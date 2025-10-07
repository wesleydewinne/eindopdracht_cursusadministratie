package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviceklasse voor alle logica die hoort bij trainers.
 * Trainers kunnen hun gegevens beheren, cursussen bekijken,
 * aanwezigheid van cursisten registreren en certificaten genereren.
 */
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

    /**
     * Haalt een specifieke trainer op basis van ID.
     *
     * @param id de unieke ID van de trainer
     * @return de trainer als gevonden, anders een exceptie
     */
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    /**
     * Werkt de gegevens van een trainer bij.
     *
     * @param id de ID van de trainer
     * @param updatedTrainer de nieuwe gegevens van de trainer
     * @return de bijgewerkte trainer
     */
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

    /**
     * Haalt alle cursussen op die aan deze trainer gekoppeld zijn.
     *
     * @param trainerId de ID van de trainer
     * @return lijst van cursussen
     */
    public List<Course> getCoursesByTrainer(Long trainerId) {
        return courseRepository.findByTrainerId(trainerId);
    }

    // ================================================================
    //  AANWEZIGHEID
    // ================================================================

    /**
     * Registreert de aanwezigheid van een cursist.
     *
     * @param registrationId de ID van de inschrijving
     * @param aanwezig true als de cursist aanwezig was, anders false
     * @return de bijgewerkte inschrijving
     */
    public Registration markAttendance(Long registrationId, boolean aanwezig) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + registrationId));

        registration.setPresent(aanwezig);
        return registrationRepository.save(registration);
    }

    // ================================================================
    //  CERTIFICATEN
    // ================================================================

    /**
     * Genereert een nieuw certificaat voor een cursist en stuurt automatisch een e-mailmelding.
     *
     * @param courseId  ID van de cursus
     * @param studentId ID van de cursist
     * @param issuedBy  Naam van de trainer of organisatie
     * @return het opgeslagen certificaat
     */
    public Certificate generateCertificate(Long courseId, Long studentId, String issuedBy) {
        // Controleer of cursus bestaat
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        // Geen certificaat voor ontruimingsoefeningen
        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalArgumentException("No certificate is issued for evacuation drills.");
        }

        // Controleer of cursist bestaat
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        // Maak nieuw certificaat aan
        LocalDate today = LocalDate.now();
        Certificate cert = new Certificate();
        cert.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        cert.setIssueDate(today);
        cert.setExpiryDate(DateHelper.calculateExpiryDate(today, course.getType()));
        cert.setIssuedBy(issuedBy != null ? issuedBy : "Safety First BV");
        cert.setStudent(student);
        cert.setCourse(course);

        // Opslaan in de database
        Certificate savedCert = certificateRepository.save(cert);

        // =============================================
        // Automatische e-mailmelding naar de cursist
        // =============================================
        String subject = "Je certificaat voor " + course.getName() + " is beschikbaar";
        String message = String.format("""
        Beste %s,

        Gefeliciteerd! Je hebt de training "%s" succesvol afgerond.
        Je certificaat met nummer %s is nu beschikbaar in het systeem.

        Je kunt het downloaden door in te loggen op je account.

        Geldig tot: %s
        Uitgegeven door: %s

        Met vriendelijke groet,
        BHV Training
        """,
                student.getName(),
                course.getName(),
                cert.getCertificateNumber(),
                cert.getExpiryDate(),
                cert.getIssuedBy()
        );

        mailHelper.sendMail(student.getEmail(), subject, message);

        return savedCert;
    }

    /**
     * Haalt alle certificaten op die gekoppeld zijn aan deze trainer.
     *
     * @param trainerId de ID van de trainer
     * @return lijst van certificaten
     */
    public List<Certificate> getCertificatesByTrainer(Long trainerId) {
        return certificateRepository.findByCourse_Trainer_Id(trainerId);
    }
}
