package nl.novi.eindopdracht_cursusadministratie.service;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    // ================================================================
    //  GEBRUIKERSBEHEER
    // ================================================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllTrainers() {
        return userRepository.findByRole(Role.TRAINER);
    }

    public List<User> getAllCursisten() {
        return userRepository.findByRole(Role.CURSIST);
    }

    public User createTrainer(User trainer) {
        trainer.setRole(Role.TRAINER);
        return userRepository.save(trainer);
    }

    public User createCursist(User cursist) {
        cursist.setRole(Role.CURSIST);
        return userRepository.save(cursist);
    }

    public User updateTrainer(Long id, User updatedTrainer) {
        return userRepository.findById(id)
                .map(trainer -> {
                    trainer.setName(updatedTrainer.getName());
                    trainer.setEmail(updatedTrainer.getEmail());
                    // Rol blijft TRAINER
                    trainer.setRole(Role.TRAINER);
                    return userRepository.save(trainer);
                })
                .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + id));
    }

    public User updateCursist(Long id, User updatedCursist) {
        return userRepository.findById(id)
                .map(cursist -> {
                    cursist.setName(updatedCursist.getName());
                    cursist.setEmail(updatedCursist.getEmail());
                    // Rol blijft CURSIST
                    cursist.setRole(Role.CURSIST);
                    return userRepository.save(cursist);
                })
                .orElseThrow(() -> new RuntimeException("Cursist not found with id: " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ================================================================
    //  CURSUSBEHEER
    // ================================================================

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

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

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
