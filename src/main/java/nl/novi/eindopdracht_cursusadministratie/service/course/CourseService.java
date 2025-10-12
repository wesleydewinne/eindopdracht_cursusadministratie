package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
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

        applyEvacuationLogic(course);

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
        existingCourse.setPhase(updatedCourse.getPhase());
        existingCourse.setReportRequired(updatedCourse.isReportRequired());

        setTrainerIfPresent(updatedCourse, existingCourse);
        setLocationIfPresent(updatedCourse, existingCourse);

        applyEvacuationLogic(existingCourse);

        return courseRepository.save(existingCourse);
    }

    // ============================================================
    // LOGICA: ONTRUIMINGSOEFENING
    // ============================================================

    /**
     * Stelt automatisch in of een verslag vereist is bij ontruimingsoefeningen.
     * TABLETOP → geen verslag, andere fasen → wel verslag.
     */
    private void applyEvacuationLogic(Course course) {
        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            if (course.getPhase() != null) {
                course.setReportRequired(course.getPhase() != nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase.TABLETOP);
            } else {
                course.setReportRequired(false);
            }
        } else {
            course.setPhase(null);
            course.setReportRequired(false);
        }
    }

    // ============================================================
    // HELPER METHODES
    // ============================================================

    private void setTrainerIfPresent(Course course) {
        if (course.getTrainer() != null && course.getTrainer().getId() != null) {
            Trainer trainer = findEntityById(course.getTrainer().getId(), trainerRepository,
                    new TrainerNotFoundException(course.getTrainer().getId()));
            course.setTrainer(trainer);
        }
    }

    private void setLocationIfPresent(Course course) {
        if (course.getLocation() != null && course.getLocation().getId() != null) {
            Location location = findEntityById(course.getLocation().getId(), locationRepository,
                    new LocationNotFoundException(course.getLocation().getId()));
            course.setLocation(location);
        }
    }

    private void setTrainerIfPresent(Course source, Course target) {
        if (source.getTrainer() != null && source.getTrainer().getId() != null) {
            Trainer trainer = findEntityById(source.getTrainer().getId(), trainerRepository,
                    new TrainerNotFoundException(source.getTrainer().getId()));
            target.setTrainer(trainer);
        }
    }

    private void setLocationIfPresent(Course source, Course target) {
        if (source.getLocation() != null && source.getLocation().getId() != null) {
            Location location = findEntityById(source.getLocation().getId(), locationRepository,
                    new LocationNotFoundException(source.getLocation().getId()));
            target.setLocation(location);
        }
    }
}
