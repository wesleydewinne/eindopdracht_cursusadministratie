package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.LocationNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.TrainerNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;
    private final LocationRepository locationRepository;

    public CourseService(CourseRepository courseRepository,
                         TrainerRepository trainerRepository,
                         LocationRepository locationRepository) {
        this.courseRepository = courseRepository;
        this.trainerRepository = trainerRepository;
        this.locationRepository = locationRepository;
    }

    // ============================================================
    // BASIS CRUD
    // ============================================================

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return findEntityById(id, courseRepository, new CourseNotFoundException(id));
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // ============================================================
    // CREATE & UPDATE
    // ============================================================

    public Course createCourse(Course course) {
        setTrainerIfPresent(course);
        setLocationIfPresent(course);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = findEntityById(id, courseRepository, new CourseNotFoundException(id));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setType(updatedCourse.getType());
        existingCourse.setStartDate(updatedCourse.getStartDate());
        existingCourse.setEndDate(updatedCourse.getEndDate());
        existingCourse.setMaxParticipants(updatedCourse.getMaxParticipants());

        setTrainerIfPresent(updatedCourse, existingCourse);
        setLocationIfPresent(updatedCourse, existingCourse);

        return courseRepository.save(existingCourse);
    }

    // ============================================================
    // HELPER METHODES
    // ============================================================

    /** Koppelt een trainer als die is opgegeven bij het aanmaken van een cursus */
    private void setTrainerIfPresent(Course course) {
        if (course.getTrainer() != null && course.getTrainer().getId() != null) {
            Trainer trainer = findEntityById(
                    course.getTrainer().getId(),
                    trainerRepository,
                    new TrainerNotFoundException(course.getTrainer().getId())
            );
            course.setTrainer(trainer);
        }
    }

    /** Koppelt een locatie als die is opgegeven bij het aanmaken van een cursus */
    private void setLocationIfPresent(Course course) {
        if (course.getLocation() != null && course.getLocation().getId() != null) {
            Location location = findEntityById(
                    course.getLocation().getId(),
                    locationRepository,
                    new LocationNotFoundException(course.getLocation().getId())
            );
            course.setLocation(location);
        }
    }

    /** Zelfde, maar bij het bijwerken van een bestaande cursus */
    private void setTrainerIfPresent(Course source, Course target) {
        if (source.getTrainer() != null && source.getTrainer().getId() != null) {
            Trainer trainer = findEntityById(
                    source.getTrainer().getId(),
                    trainerRepository,
                    new TrainerNotFoundException(source.getTrainer().getId())
            );
            target.setTrainer(trainer);
        }
    }

    private void setLocationIfPresent(Course source, Course target) {
        if (source.getLocation() != null && source.getLocation().getId() != null) {
            Location location = findEntityById(
                    source.getLocation().getId(),
                    locationRepository,
                    new LocationNotFoundException(source.getLocation().getId())
            );
            target.setLocation(location);
        }
    }
}
