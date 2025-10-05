package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, LocationRepository locationRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    // ✅ Alle cursussen ophalen
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // ✅ Cursus aanmaken met trainer + locatie
    public Course createCourse(Course course) {
        if (course.getTrainer() != null && course.getTrainer().getId() != null) {
            User trainer = userRepository.findById(course.getTrainer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found with ID: " + course.getTrainer().getId()));
            course.setTrainer(trainer);
        }

        if (course.getLocation() != null && course.getLocation().getId() != null) {
            Location location = locationRepository.findById(course.getLocation().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + course.getLocation().getId()));
            course.setLocation(location);
        }

        return courseRepository.save(course);
    }

    // ✅ Cursus ophalen op ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
    }

    // ✅ Cursus bijwerken
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setTitle(updatedCourse.getTitle());
                    existingCourse.setType(updatedCourse.getType());
                    existingCourse.setDate(updatedCourse.getDate());

                    if (updatedCourse.getTrainer() != null && updatedCourse.getTrainer().getId() != null) {
                        User trainer = userRepository.findById(updatedCourse.getTrainer().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Trainer not found with ID: " + updatedCourse.getTrainer().getId()));
                        existingCourse.setTrainer(trainer);
                    }

                    if (updatedCourse.getLocation() != null && updatedCourse.getLocation().getId() != null) {
                        Location location = locationRepository.findById(updatedCourse.getLocation().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + updatedCourse.getLocation().getId()));
                        existingCourse.setLocation(location);
                    }

                    return courseRepository.save(existingCourse);
                })
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
    }

    // ✅ Cursus verwijderen
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
