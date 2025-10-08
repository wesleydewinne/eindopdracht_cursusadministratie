package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.RegistrationNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.TrainerNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
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

    /**
     * Haalt een specifieke trainer op basis van ID.
     *
     * @param id de unieke ID van de trainer
     * @return de trainer als gevonden, anders een exceptie
     */
    public Trainer getTrainerById(Long id) {
        return findEntityById(id, trainerRepository, new TrainerNotFoundException(id));
    }

    /**
     * Werkt de gegevens van een trainer bij.
     *
     * @param id de ID van de trainer
     * @param updatedTrainer de nieuwe gegevens van de trainer
     * @return de bijgewerkte trainer
     */
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
        Registration registration = findEntityById(registrationId, registrationRepository, new RegistrationNotFoundException(registrationId));
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
        //  Ophalen van de vereiste entiteiten
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User student = findEntityById(studentId, userRepository, new CursistNotFoundException(studentId));

        // Geen certificaat voor ontruimingsoefeningen
        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("No certificate is issued for evacuation drills.");
        }

        //  Nieuw certificaat opbouwen
        LocalDate today = LocalDate.now();
        Certificate cert = new Certificate();
        cert.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        cert.setIssueDate(today);
        cert.setExpiryDate(DateHelper.calculateExpiryDate(today, course.getType()));
        cert.setIssuedBy(issuedBy != null ? issuedBy : "BHV Training");
        cert.setStudent(student);
        cert.setCourse(course);

        Certificate savedCert = certificateRepository.save(cert);

        // =============================================
        //  Automatische e-mailmelding via MailHelper
        // =============================================
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
