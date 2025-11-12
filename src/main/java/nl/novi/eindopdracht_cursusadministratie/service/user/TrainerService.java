package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseCompletionSummaryDto;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationStatusResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.TrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.TrainerUpdateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.helper.*;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

/**
 * Serviceklasse voor trainers.
 *
 * Bevat logica voor:
 * - profielbeheer
 * - cursussen bekijken en afsluiten
 * - aanwezigheid markeren
 * - beoordelingen uitvoeren
 * - certificaten genereren
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
    private final PasswordEncoder passwordEncoder;

    // ============================================================
    // TRAINER PROFIEL
    // ============================================================

    public TrainerResponseDto getTrainerById(Long id) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Trainer trainer = findEntityById(id, trainerRepository, new TrainerNotFoundException(id));
        return toTrainerDto(trainer);
    }

    public TrainerResponseDto updateTrainer(Long id, TrainerUpdateRequestDto dto) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Trainer trainer = findEntityById(id, trainerRepository, new TrainerNotFoundException(id));

        trainer.setName(dto.getName());
        trainer.setEmail(dto.getEmail());
        trainer.setExpertise(dto.getExpertise());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            trainer.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        trainerRepository.save(trainer);
        return toTrainerDto(trainer);
    }

    // ============================================================
    // CURSUSSEN
    // ============================================================

    public List<CourseTrainerResponseDto> getCoursesByTrainer(Long trainerId) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        List<Course> courses = courseRepository.findByTrainer_Id(trainerId);

        return courses.stream()
                .map(course -> new CourseTrainerResponseDto(
                        course.getId(),
                        course.getName(),
                        course.getStartDate(),
                        course.getEndDate(),
                        course.getRegistrations() != null ? course.getRegistrations().size() : 0,
                        LocationMapperHelper.toTrainerDto(course.getLocation()),
                        course.getRegistrations().stream()
                                .map(RegistrationMapperHelper::toTrainerDto)
                                .toList()
                ))
                .toList();
    }

    /**
     * Sluit een cursus af.
     * - Zet automatisch alle afwezigen (present=false) op ABSENT.
     * - Controleert of alle aanwezigen beoordeeld zijn.
     */
    public CourseCompletionSummaryDto completeCourse(Long courseId) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        List<Registration> notReviewed = course.getRegistrations().stream()
                .filter(Registration::isPresent)
                .filter(r -> r.getStatus() == RegistrationStatus.PENDING)
                .toList();

        if (!notReviewed.isEmpty()) {
            String details = notReviewed.stream()
                    .map(r -> String.format("%s (registrationId: %d)", r.getStudent().getName(), r.getId()))
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("onbekend");

            throw new IllegalStateException("Niet alle aanwezige cursisten zijn beoordeeld: " + details);
        }

        course.getRegistrations().stream()
                .filter(r -> !r.isPresent())
                .forEach(r -> r.setStatus(RegistrationStatus.ABSENT));

        registrationRepository.saveAll(course.getRegistrations());

        List<String> approved = course.getRegistrations().stream()
                .filter(r -> r.getStatus() == RegistrationStatus.APPROVED)
                .map(r -> r.getStudent().getName())
                .toList();

        List<String> cancelled = course.getRegistrations().stream()
                .filter(r -> r.getStatus() == RegistrationStatus.CANCELLED)
                .map(r -> r.getStudent().getName())
                .toList();

        List<String> absent = course.getRegistrations().stream()
                .filter(r -> r.getStatus() == RegistrationStatus.ABSENT)
                .map(r -> r.getStudent().getName())
                .toList();

        return new CourseCompletionSummaryDto(
                course.getId(),
                course.getName(),
                course.getTrainer().getName(),
                approved,
                cancelled,
                absent
        );
    }




    // ============================================================
    // REGISTRATIES / AANWEZIGHEID / STATUS
    // ============================================================

    public RegistrationStatusResponseDto markAttendanceForCourse(Long courseId, Long registrationId, boolean present) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Registration registration = findEntityById(
                registrationId, registrationRepository, new RegistrationNotFoundException(registrationId));

        if (!registration.getCourse().getId().equals(courseId)) {
            throw new IllegalStateException("Deze inschrijving hoort niet bij de opgegeven cursus.");
        }

        registration.updateAttendance(present);
        registrationRepository.save(registration);

        return toStatusResponse(registration);
    }

    /**
     * Beoordeelt een cursist, alleen als deze aanwezig was.
     */
    public RegistrationStatusResponseDto updateRegistrationStatusForCourse(
            Long courseId, Long registrationId, RegistrationStatus status
    ) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Registration registration = findEntityById(
                registrationId, registrationRepository, new RegistrationNotFoundException(registrationId));

        if (!registration.getCourse().getId().equals(courseId)) {
            throw new IllegalStateException("De inschrijving hoort niet bij deze cursus.");
        }

        if (!registration.isPresent()) {
            throw new IllegalStateException("Deze cursist was niet aanwezig en kan niet beoordeeld worden.");
        }

        registration.setStatus(status);

        if (status == RegistrationStatus.APPROVED) {
            generateCertificateForCompletedCourse(registration);
        }

        registrationRepository.save(registration);
        return toStatusResponse(registration);
    }

    /**
     * Haalt alle aanwezige maar nog niet beoordeelde cursisten op.
     */
    public List<RegistrationStatusResponseDto> getUnreviewedRegistrationsForCourse(Long courseId) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));

        return course.getRegistrations().stream()
                .filter(r -> r.isPresent() && r.getStatus() == RegistrationStatus.PENDING)
                .map(RegistrationMapperHelper::toStatusDto)
                .toList();
    }

    // ============================================================
    // CERTIFICATEN
    // ============================================================

    private void generateCertificateForCompletedCourse(Registration registration) {
        Course course = registration.getCourse();
        User student = registration.getStudent();

        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) return;

        boolean exists = certificateRepository
                .findByCourse_IdAndStudent_Id(course.getId(), student.getId())
                .stream()
                .findFirst()
                .isPresent();

        if (exists) return;

        createAndSaveCertificate(course, student, course.getTrainer().getName());
    }

    public Certificate generateCertificate(Long courseId, Long studentId, String issuedBy) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        User student = findEntityById(studentId, userRepository, new CursistNotFoundException(studentId));

        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("Er wordt geen certificaat uitgegeven voor ontruimingsoefeningen.");
        }

        return createAndSaveCertificate(course, student, issuedBy != null ? issuedBy : "BHV Training");
    }

    private Certificate createAndSaveCertificate(Course course, User student, String issuedBy) {
        LocalDate today = LocalDate.now();

        Certificate cert = new Certificate();
        cert.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        cert.setIssueDate(today);
        cert.setExpiryDate(DateHelper.calculateExpiryDateOrIssue(today, course.getType()));
        cert.setIssuedBy(issuedBy);
        cert.setStudent(student);
        cert.setCourse(course);

        Certificate saved = certificateRepository.save(cert);

        mailHelper.sendCertificateNotification(
                student.getEmail(),
                student.getName(),
                course.getName(),
                saved.getCertificateNumber(),
                saved.getExpiryDate().toString(),
                saved.getIssuedBy()
        );

        return saved;
    }

    public List<Certificate> getCertificatesByTrainer(Long trainerId) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();
        return certificateRepository.findByCourse_Trainer_Id(trainerId);
    }

    // ============================================================
    // HULPFUNCTIES / MAPPERS
    // ============================================================

    public Course getCourseEntity(Long courseId) {
        return findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
    }

    private TrainerResponseDto toTrainerDto(Trainer trainer) {
        return new TrainerResponseDto(
                trainer.getId(),
                trainer.getName(),
                trainer.getEmail(),
                trainer.getRole().name(),
                trainer.getExpertise()
        );
    }

    private RegistrationStatusResponseDto toStatusResponse(Registration registration) {
        return new RegistrationStatusResponseDto(
                registration.getId(),
                registration.getStudent().getId(),
                registration.getStudent().getName(),
                registration.isPresent(),
                registration.getStatus(),
                registration.getCourse().getName()
        );
    }
}
