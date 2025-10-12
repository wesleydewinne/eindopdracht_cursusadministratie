package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.UserNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final CourseService courseService;

    // ============================================================
    // GEBRUIKERSBEHEER
    // ============================================================

    /** Alle gebruikers ophalen */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** Gebruiker verwijderen */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    // ============================================================
    // CURSISTEN
    // ============================================================

    /** Alle cursisten ophalen */
    public List<Cursist> getAllCursisten() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Cursist)
                .map(u -> (Cursist) u)
                .toList();
    }

    /** Nieuwe cursist aanmaken */
    public Cursist createCursist(User user) {
        if (!(user instanceof Cursist cursist)) {
            throw new IllegalArgumentException("Het opgegeven object is geen cursist.");
        }
        return userRepository.save(cursist);
    }

    /** Cursist bijwerken */
    public Cursist updateCursist(Long id, User updatedUser) {
        if (!(updatedUser instanceof Cursist updatedCursist)) {
            throw new IllegalArgumentException("Het bijgewerkte object is geen cursist.");
        }

        Cursist existing = (Cursist) userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setName(updatedCursist.getName());
        existing.setEmail(updatedCursist.getEmail());
        existing.setActive(updatedCursist.isActive());

        return userRepository.save(existing);
    }

    // ============================================================
    // TRAINERS
    // ============================================================

    /** Alle trainers ophalen */
    public List<Trainer> getAllTrainers() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Trainer)
                .map(u -> (Trainer) u)
                .toList();
    }

    /** Nieuwe trainer aanmaken */
    public Trainer createTrainer(User user) {
        if (!(user instanceof Trainer trainer)) {
            throw new IllegalArgumentException("Het opgegeven object is geen trainer.");
        }
        return userRepository.save(trainer);
    }

    /** Trainer bijwerken */
    public Trainer updateTrainer(Long id, User updatedUser) {
        if (!(updatedUser instanceof Trainer updatedTrainer)) {
            throw new IllegalArgumentException("Het bijgewerkte object is geen trainer.");
        }

        Trainer existing = (Trainer) userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setName(updatedTrainer.getName());
        existing.setEmail(updatedTrainer.getEmail());
        existing.setExpertise(updatedTrainer.getExpertise());

        return userRepository.save(existing);
    }

    // ============================================================
    // CURSUSSEN
    // ============================================================

    /** Alle cursussen ophalen */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /** Nieuwe cursus aanmaken (via CourseService) */
    public Course createCourse(Course course) {
        return courseService.createCourse(course);
    }

    /** Cursus bijwerken */
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseService.updateCourse(id, updatedCourse);
    }

    /** Cursus verwijderen */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // ============================================================
    // REGISTRATIES
    // ============================================================

    /** Registratie verwijderen */
    public void deleteRegistration(Long registrationId) {
        if (!registrationRepository.existsById(registrationId)) {
            throw new IllegalArgumentException("Registratie met ID " + registrationId + " bestaat niet.");
        }
        registrationRepository.deleteById(registrationId);
    }
}
