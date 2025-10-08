package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.TrainerNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    private final PasswordEncoder passwordEncoder;

    // ================================================================
    //  GEBRUIKERSBEHEER
    // ================================================================

    /** Alle gebruikers ophalen */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** Alle trainers ophalen */
    public List<User> getAllTrainers() {
        return userRepository.findByRole(Role.TRAINER);
    }

    /** Alle cursisten ophalen */
    public List<User> getAllCursisten() {
        return userRepository.findByRole(Role.CURSIST);
    }

    /** Nieuwe trainer aanmaken (met versleuteld wachtwoord) */
    public User createTrainer(User trainer) {
        trainer.setRole(Role.TRAINER);

        if (trainer.getPassword() != null && !trainer.getPassword().isBlank()) {
            trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
        }

        return userRepository.save(trainer);
    }

    /** Nieuwe cursist aanmaken (met versleuteld wachtwoord) */
    public User createCursist(User cursist) {
        cursist.setRole(Role.CURSIST);

        if (cursist.getPassword() != null && !cursist.getPassword().isBlank()) {
            cursist.setPassword(passwordEncoder.encode(cursist.getPassword()));
        }

        return userRepository.save(cursist);
    }

    /** Trainer bijwerken */
    public User updateTrainer(Long id, User updatedTrainer) {
        User existing = findEntityById(id, userRepository, new TrainerNotFoundException(id));
        existing.setName(updatedTrainer.getName());
        existing.setEmail(updatedTrainer.getEmail());
        existing.setRole(Role.TRAINER);

        if (updatedTrainer.getPassword() != null && !updatedTrainer.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedTrainer.getPassword()));
        }

        return userRepository.save(existing);
    }

    /** Cursist bijwerken */
    public User updateCursist(Long id, User updatedCursist) {
        User existing = findEntityById(id, userRepository, new CursistNotFoundException(id));
        existing.setName(updatedCursist.getName());
        existing.setEmail(updatedCursist.getEmail());
        existing.setRole(Role.CURSIST);

        if (updatedCursist.getPassword() != null && !updatedCursist.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedCursist.getPassword()));
        }

        return userRepository.save(existing);
    }

    /** Gebruiker verwijderen (trainer of cursist) */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // ================================================================
    //  INSCHRIJVINGSBEHEER
    // ================================================================

    /** Inschrijving verwijderen (door Admin) */
    public void deleteRegistration(Long registrationId) {
        if (!registrationRepository.existsById(registrationId)) {
            throw new RuntimeException("Registration not found with id: " + registrationId);
        }
        registrationRepository.deleteById(registrationId);
    }

    // ================================================================
    //  CURSUSBEHEER
    // ================================================================

    /** Alle cursussen ophalen */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /** Nieuwe cursus aanmaken */
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    /** Bestaande cursus bijwerken (locatie, trainer, data, etc.) */
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existing = findEntityById(id, courseRepository, new CourseNotFoundException(id));
        existing.setName(updatedCourse.getName());
        existing.setDescription(updatedCourse.getDescription());
        existing.setStartDate(updatedCourse.getStartDate());
        existing.setEndDate(updatedCourse.getEndDate());
        existing.setLocation(updatedCourse.getLocation());
        existing.setMaxParticipants(updatedCourse.getMaxParticipants());
        existing.setTrainer(updatedCourse.getTrainer());
        return courseRepository.save(existing);
    }

    /** Cursus verwijderen */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }
}