
package nl.novi.eindopdracht_cursusadministratie.controller.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    // Alle cursussen ophalen
    @GetMapping
    public List<Course> getAllCourses() {
        return service.getAllCourses();
    }

    // Cursus ophalen op ID
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return service.getCourseById(id);
    }

    // Cursus aanmaken (met trainer + locatie)
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return service.createCourse(course);
    }

    // Cursus verwijderen
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        service.deleteCourse(id);
    }
}
