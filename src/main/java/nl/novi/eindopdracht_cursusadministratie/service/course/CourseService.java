package nl.novi.eindopdracht_cursusadministratie.service.course;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

/**
 * Serviceklasse voor het beheren van cursussen (BHV, EHBO en Ontruimingsoefeningen).
 *
 * Rollen:
 *  - ADMIN → kan cursussen aanmaken, bijwerken en verwijderen
 *  - TRAINER → kan alleen eigen cursussen bekijken
 *  - CURSIST → kan beschikbare BHV/EHBO cursussen bekijken (geen ontruimingsoefeningen)
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;
    private final LocationRepository locationRepository;

    // ============================================================
    // ALGEMENE CRUD-ACTIES
    // ============================================================

    /** Haalt alle cursussen op (alleen admin). */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /** Haalt één cursus op via ID. */
    public Course getCourseById(Long id) {
        return findEntityById(id, courseRepository, new CourseNotFoundException(id));
    }

    /** Verwijdert een cursus op basis van ID. */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // ============================================================
    // CREATE & UPDATE
    // ============================================================

    /**
     * Maakt een nieuwe cursus aan.
     * Trainer en locatie worden gevalideerd; verslaglogica wordt automatisch toegepast.
     */
    public Course createCourse(Course course) {

        setTrainerIfPresent(course);
        setLocationIfPresent(course);

        if (course.getType() == TrainingType.BHV || course.getType() == TrainingType.EHBO) {
            if (course.getMaxParticipants() == null) {
                throw new IllegalArgumentException("Maximaal aantal deelnemers is verplicht voor dit type training.");
            }

            if (course.getMaxParticipants() < 3 || course.getMaxParticipants() > 10) {
                throw new IllegalArgumentException("Het maximaal aantal deelnemers moet tussen 3 en 10 liggen.");
            }
        }

        applyTrainingTypeLogic(course);

        return courseRepository.save(course);
    }


    /**
     * Wijzigt een bestaande cursus.
     * Type, fase en verslaglogica worden automatisch herberekend.
     */
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = findEntityById(id, courseRepository, new CourseNotFoundException(id));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setType(updatedCourse.getType());
        existingCourse.setStartDate(updatedCourse.getStartDate());
        existingCourse.setEndDate(updatedCourse.getEndDate());
        existingCourse.setMaxParticipants(updatedCourse.getMaxParticipants());
        existingCourse.setAdminOverrideAllowed(updatedCourse.isAdminOverrideAllowed());
        existingCourse.setPhase(updatedCourse.getPhase());

        setTrainerIfPresent(updatedCourse, existingCourse);
        setLocationIfPresent(updatedCourse, existingCourse);

        applyTrainingTypeLogic(existingCourse);

        return courseRepository.save(existingCourse);
    }

    // ============================================================
    // TRAINER & CURSIST ACTIES
    // ============================================================

    /** Haalt alle cursussen op die door een specifieke trainer worden gegeven. */
    public List<Course> getCoursesByTrainer(Long trainerId) {
        return courseRepository.findByTrainer_Id(trainerId);
    }

    /**
     * Haalt alle cursussen op die beschikbaar zijn voor cursisten.
     * Filtert ontruimingsoefeningen eruit.
     */
    public List<Course> getAvailableCoursesForStudents() {
        return courseRepository.findAll().stream()
                .filter(c -> !c.isEvacuationTraining()) // cursisten zien geen ontruimingsoefeningen
                .toList();
    }

    // ============================================================
    // TRAININGTYPE & LOGICA
    // ============================================================

    /**
     * Past logica toe op basis van het TrainingType.
     * - BHV/EHBO → geen verslag, wel deelnemerslimiet
     * - ONTRUIMINGSOEFENING → mogelijk verslag, geen deelnemerslimiet
     */
    public void applyTrainingTypeLogic(Course course) {
        TrainingType type = course.getType();

        if (type == null) {
            throw new IllegalArgumentException("TrainingType mag niet null zijn.");
        }

        switch (type) {
            case BHV, EHBO -> {
                course.setReportRequired(false);
                course.setPhase(null);
                if (course.getMaxParticipants() == null || course.getMaxParticipants() <= 0) {
                    course.setMaxParticipants(10);
                }
            }
            case ONTRUIMINGSOEFENING -> {
                course.setMaxParticipants(null);

                if (course.getPhase() == null) {
                    course.setReportRequired(false);
                } else {
                    course.setReportRequired(course.getPhase() != EvacuationPhase.TABLETOP);
                }
            }
            default -> throw new IllegalStateException("Onbekend trainingstype: " + type);
        }
    }

    // ============================================================
    // HELPERMETHODES
    // ============================================================

    /** Koppelt de trainer aan de cursus indien aanwezig. */
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

    /** Koppelt de locatie aan de cursus indien aanwezig. */
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

    /** Kopieert de trainer van bron naar doel als aanwezig. */
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

    /** Kopieert de locatie van bron naar doel als aanwezig. */
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
