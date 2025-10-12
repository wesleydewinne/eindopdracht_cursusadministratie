package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CourseRepository courseRepository;
    private final CourseService courseService; // hergebruik logica

    // overige velden en methoden laat je zoals ze zijn...

    /** Alle cursussen ophalen */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /** Nieuwe cursus aanmaken via CourseService-logica */
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
}
