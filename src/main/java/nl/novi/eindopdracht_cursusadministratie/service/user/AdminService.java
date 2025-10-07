package nl.novi.eindopdracht_cursusadministratie.service;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.CreateCourseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.CourseResponseDto;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
