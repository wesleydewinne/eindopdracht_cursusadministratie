package nl.novi.eindopdracht_cursusadministratie.service.registration;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationCreateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationStatusResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.helper.RegistrationHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.RegistrationMapperHelper;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

/**
 * Serviceklasse voor beheer van inschrijvingen (registraties).
 *
 * Rollen:
 * - ADMIN → kan alle inschrijvingen bekijken, aanmaken en verwijderen
 * - TRAINER → kan alleen inschrijvingen van eigen cursussen beheren (aanwezigheid en status)
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // ============================================================
    // ALGEMENE CRUD & OPVRAGEN
    // ============================================================

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(Long id) {
        return findEntityById(id, registrationRepository, new RegistrationNotFoundException(id));
    }

    public List<Registration> getRegistrationsByTrainer(Long trainerId) {
        return registrationRepository.findByCourse_Trainer_Id(trainerId);
    }

    public List<Registration> getRegistrationsByCourse(Long courseId) {
        return registrationRepository.findByCourse_Id(courseId);
    }

    // ============================================================
    // TRAINER ACTIES
    // ============================================================

    public RegistrationStatusResponseDto updateAttendanceAndStatus(
            Long registrationId,
            boolean attendance,
            RegistrationStatus status,
            User trainer
    ) {
        Registration registration = getRegistrationById(registrationId);

        if (registration.getCourse() == null || registration.getCourse().getTrainer() == null) {
            throw new UnauthorizedAccessException("Deze inschrijving is niet gekoppeld aan een trainer.");
        }

        if (!registration.getCourse().getTrainer().getId().equals(trainer.getId())) {
            throw new UnauthorizedAccessException("Je kunt alleen inschrijvingen beheren van je eigen cursussen.");
        }

        registration.updateAttendance(attendance);

        if (status != null) {
            registration.setStatus(status);
        }

        RegistrationHelper.evaluateCompletion(registration);

        registrationRepository.save(registration);

        return RegistrationMapperHelper.toStatusDto(registration);
    }

    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    /**
     * Admin kan een nieuwe inschrijving aanmaken (cursist toevoegen aan cursus).
     * Controleert automatisch op maximum aantal deelnemers, duplicaten en uitzonderingen.
     */
    public Registration createRegistration(RegistrationCreateRequestDto dto) {

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException(dto.getCourseId()));

        Cursist student = (Cursist) userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new UserNotFoundException(dto.getStudentId()));

        boolean alreadyRegistered = registrationRepository
                .existsByCourse_IdAndStudent_Id(dto.getCourseId(), dto.getStudentId());

        if (alreadyRegistered) {
            throw new BadRequestException("Cursist is al ingeschreven voor deze cursus.");
        }

        long currentCount = registrationRepository.countByCourse_Id(dto.getCourseId());

        boolean limitEnforced = !course.isAdminOverrideAllowed()
                && course.getType() != TrainingType.ONTRUIMINGSOEFENING;

        if (limitEnforced && currentCount >= course.getMaxParticipants()) {
            throw new CapacityExceededException(
                    "Cursus '" + course.getName() + "' zit vol (maximaal "
                            + course.getMaxParticipants() + " deelnemers).");
        }

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus(RegistrationStatus.REGISTERED);

        return registrationRepository.save(registration);
    }

    /**
     * Admin kan de status van een registratie wijzigen.
     */
    public RegistrationStatusResponseDto updateStatusAsAdmin(
            Long registrationId,
            RegistrationStatus newStatus,
            Long adminId
    ) {
        findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        Registration registration = getRegistrationById(registrationId);
        registration.setStatus(newStatus);

        registrationRepository.save(registration);

        return RegistrationMapperHelper.toStatusDto(registration);
    }

    /**
     * Admin kan een inschrijving verwijderen (alleen de koppeling tussen cursus en cursist).
     */
    public Registration deleteRegistrationAndReturn(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RegistrationNotFoundException(registrationId));

        registrationRepository.delete(registration);
        return registration;
    }

}
