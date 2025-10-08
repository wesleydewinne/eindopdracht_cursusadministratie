package nl.novi.eindopdracht_cursusadministratie.service.registration;

import nl.novi.eindopdracht_cursusadministratie.dto.response.RegistrationResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.RegistrationNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               CourseRepository courseRepository,
                               UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // BASIS CRUD
    // ============================================================

    @SuppressWarnings("unused")
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(Long id) {
        return findEntityById(id, registrationRepository, new RegistrationNotFoundException(id));
    }

    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RegistrationNotFoundException(id);
        }
        registrationRepository.deleteById(id);
    }

    // ============================================================
    // CREATE & UPDATE
    // ============================================================

    public Registration createRegistration(Long courseId, Long studentId) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User student = findEntityById(studentId, userRepository, new CursistNotFoundException(studentId));

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus(RegistrationStatus.PENDING);

        return registrationRepository.save(registration);
    }

    public Registration updateRegistrationStatus(Long id, RegistrationStatus status) {
        Registration registration = findEntityById(id, registrationRepository, new RegistrationNotFoundException(id));
        registration.setStatus(status);
        return registrationRepository.save(registration);
    }

    // ============================================================
    // DTO-CONVERSIES
    // ============================================================

    private RegistrationResponseDto toDto(Registration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getCourse().getId(),
                registration.getCourse().getName(),
                registration.getStudent().getId(),
                registration.getStudent().getName(),
                registration.getRegistrationDate(),
                registration.getStatus()
        );
    }

    public List<RegistrationResponseDto> getAllRegistrationDtos() {
        return registrationRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public RegistrationResponseDto getRegistrationDtoById(Long id) {
        return toDto(getRegistrationById(id));
    }

    public RegistrationResponseDto createRegistrationDto(Long courseId, Long studentId) {
        return toDto(createRegistration(courseId, studentId));
    }

    public RegistrationResponseDto updateRegistrationStatusDto(Long id, RegistrationStatus status) {
        return toDto(updateRegistrationStatus(id, status));
    }
}
