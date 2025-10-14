package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Trainer trainer;
    private Location location;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setName("Jan Trainer");

        location = new Location();
        location.setId(1L);
        location.setCompanyName("BHV Amsterdam");

        course = new Course();
        course.setId(1L);
        course.setName("BHV Basis");
        course.setType(TrainingType.BHV);
        course.setTrainer(trainer);
        course.setLocation(location);
    }

    // Arrange / Act / Assert pattern toegepast

    @Test
    void shouldReturnAllCourses_whenGetAllCoursesIsCalled() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(java.util.List.of(course));

        // Act
        var result = courseService.getAllCourses();

        // Assert
        assertEquals(1, result.size());
        assertEquals("BHV Basis", result.get(0).getName());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowException_whenCourseNotFound() {
        // Arrange
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(99L));
    }

    @Test
    void shouldCreateCourse_whenTrainerAndLocationExist() {
        // Arrange
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Course saved = courseService.createCourse(course);

        // Assert
        assertNotNull(saved);
        assertEquals("BHV Basis", saved.getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldApplyEvacuationLogic_whenCourseIsEvacuationType() {
        // Arrange
        course.setType(TrainingType.ONTRUIMINGSOEFENING);
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Course saved = courseService.createCourse(course);

        // Assert
        assertNotNull(saved);
        assertEquals(TrainingType.ONTRUIMINGSOEFENING, saved.getType());
        // Ontruimingsoefening mag geen verslagvereiste hebben bij TABLETOP
        assertFalse(saved.isReportRequired());
    }

    @Test
    void shouldDeleteCourse_whenCourseExists() {
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(true);

        // Act
        courseService.deleteCourse(1L);

        // Assert
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistingCourse() {
        // Arrange
        when(courseRepository.existsById(5L)).thenReturn(false);

        // Act + Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(5L));
    }
}
