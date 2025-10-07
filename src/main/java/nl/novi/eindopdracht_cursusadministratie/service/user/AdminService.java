package nl.novi.eindopdracht_cursusadministratie.service;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    // ================================================================
    // 🔹 GEBRUIKERSBEHEER
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

    /** Nieuwe trainer aanmaken */
    public User createTrainer(User trainer) {
        trainer.setRole(Role.TRAINER);
        return userRepository.save(trainer);
    }

    /** Nieuwe cursist aanmaken */
    public User createCursist(User cursist) {
        cursist.setRole(Role.CURSIST);
        return userRepository.save(cursist);
    }

    /** Trainer bijwerken */
    public User updateTrainer(Long id, User updatedTrainer) {
        return userRepository.findById(id)
                .map(trainer -> {
                    trainer.setName(updatedTrainer.getName());
                    trainer.setEmail(updatedTrainer.getEmail());
                    trainer.setRole(Role.TRAINER); // Rol blijft TRAINER
                    return userRepository.save(trainer);
                })
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    /** Cursist bijwerken */
    public User updateCursist(Long id, User updatedCursist) {
        return userRepository.findById(id)
                .map(cursist -> {
                    cursist.setName(updatedCursist.getName());
                    cursist.setEmail(updatedCursist.getEmail());
                    cursist.setRole(Role.CURSIST); // Rol blijft CURSIST
                    return userRepository.save(cursist);
                })
                .orElseThrow(() -> new RuntimeException("Cursist not found with id: " + id));
    }

    /** Gebruiker verwijderen (trainer of cursist) */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ================================================================
    // 🔹 INSCHRIJVINGSBEHEER
    // ================================================================

    /** Inschrijving verwijderen (door Admin) */
    public void deleteRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }

    // ================================================================
    // 🔹 CURSUSBEHEER
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
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    course.setDescription(updatedCourse.getDescription());
                    course.setStartDate(updatedCourse.getStartDate());
                    course.setEndDate(updatedCourse.getEndDate());
                    course.setLocation(updatedCourse.getLocation());
                    course.setMaxParticipants(updatedCourse.getMaxParticipants());
                    course.setTrainer(updatedCourse.getTrainer());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    /** Cursus verwijderen */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
