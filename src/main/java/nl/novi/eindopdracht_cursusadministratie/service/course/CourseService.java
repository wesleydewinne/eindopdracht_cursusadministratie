package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // Alle cursussen ophalen
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Cursus aanmaken + trainer koppelen
    public Course createCourse(Course course) {
        if (course.getTrainer() != null && course.getTrainer().getId() != null) {
            User trainer = userRepository.findById(course.getTrainer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));
            course.setTrainer(trainer);
        }
        return courseRepository.save(course);
    }

    // Cursus ophalen op ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    // Cursus verwijderen
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
