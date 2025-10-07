package nl.novi.eindopdracht_cursusadministratie.service.registration;

import nl.novi.eindopdracht_cursusadministratie.dto.response.RegistrationResponseDto;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with ID: " + id));
    }

    public Registration createRegistration(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Registration registration = new Registration();
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setRegistrationDate(LocalDate.now());
        registration.setStatus(RegistrationStatus.PENDING);

        return registrationRepository.save(registration);
    }

    public Registration updateRegistrationStatus(Long id, RegistrationStatus status) {
        Registration registration = getRegistrationById(id);
        registration.setStatus(status);
        return registrationRepository.save(registration);
    }

    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new IllegalArgumentException("Registration not found with ID: " + id);
        }
        registrationRepository.deleteById(id);
    }

    private RegistrationResponseDto toDto(Registration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getCourse().getId(),
                registration.getCourse().getTitle(),
                registration.getStudent().getId(),
                registration.getStudent().getName(),
                registration.getRegistrationDate(),
                registration.getStatus()
        );
    }

    // DTO-versies van CRUD-methodes
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
