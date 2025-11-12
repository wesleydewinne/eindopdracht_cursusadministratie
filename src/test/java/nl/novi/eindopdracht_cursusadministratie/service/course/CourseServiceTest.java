package nl.novi.eindopdracht_cursusadministratie.service.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private CourseService courseService;

    private Course bhvCourse;
    private Course evacuationCourse;
    private Trainer trainer;
    private Location location;

    // ============================================================
    // SETUP & TEST DATA
    // ============================================================
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setName("Trainer X");

        location = new Location();
        location.setId(2L);
        location.setCompanyName("Bedrijf BV");

        bhvCourse = new Course();
        bhvCourse.setId(10L);
        bhvCourse.setName("BHV Basiscursus");
        bhvCourse.setType(TrainingType.BHV);
        bhvCourse.setMaxParticipants(10);
        bhvCourse.setStartDate(LocalDate.now());
        bhvCourse.setEndDate(LocalDate.now().plusDays(1));
        bhvCourse.setTrainer(trainer);
        bhvCourse.setLocation(location);

        evacuationCourse = new Course();
        evacuationCourse.setId(20L);
        evacuationCourse.setName("Ontruimingsoefening Fase 3");
        evacuationCourse.setType(TrainingType.ONTRUIMINGSOEFENING);
        evacuationCourse.setPhase(EvacuationPhase.UNANNOUNCED_EVACUATION);
    }

    // ============================================================
    // CREATE COURSE
    // ============================================================
    @Test
    @DisplayName("Admin kan een BHV-cursus aanmaken met geldige data")
    void createCourse() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(location));
        when(courseRepository.save(any(Course.class))).thenReturn(bhvCourse);

        Course result = courseService.createCourse(bhvCourse);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(TrainingType.BHV);
        assertThat(result.getMaxParticipants()).isEqualTo(10);
        verify(courseRepository).save(any(Course.class));
    }

    // --- VALIDATION RULES ---
    @Test
    @DisplayName("Bij ongeldige maxParticipants wordt IllegalArgumentException gegooid")
    void createCourse_InvalidMaxParticipants() {

        bhvCourse.setType(TrainingType.BHV);
        bhvCourse.setMaxParticipants(0);
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(new Trainer()));
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(new Location()));

        assertThatThrownBy(() -> courseService.createCourse(bhvCourse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tussen 3 en 10");
    }

    @Test
    @DisplayName("Ontruimingsoefening mag geen deelnemerslimiet hebben")
    void createCourse_EvacuationTraining_NoLimit() {
        when(courseRepository.save(any(Course.class))).thenReturn(evacuationCourse);

        Course result = courseService.createCourse(evacuationCourse);

        assertThat(result.getMaxParticipants()).isNull();
        verify(courseRepository).save(evacuationCourse);
    }

    // ============================================================
    // UPDATE COURSE
    // ============================================================
    @Test
    @DisplayName("Admin kan bestaande cursus bijwerken")
    void updateCourse() {
        when(courseRepository.findById(10L)).thenReturn(Optional.of(bhvCourse));
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(locationRepository.findById(2L)).thenReturn(Optional.of(location));
        when(courseRepository.save(any(Course.class))).thenReturn(bhvCourse);

        Course updated = new Course();
        updated.setName("BHV Herhaling");
        updated.setDescription("Herhalingscursus BHV");
        updated.setType(TrainingType.BHV);
        updated.setTrainer(trainer);
        updated.setLocation(location);
        updated.setMaxParticipants(8);

        Course result = courseService.updateCourse(10L, updated);

        assertThat(result.getName()).isEqualTo("BHV Herhaling");
        assertThat(result.getMaxParticipants()).isEqualTo(8);
        verify(courseRepository).save(any(Course.class));
    }

    // ============================================================
    // GET AVAILABLE COURSES
    // ============================================================
    @Test
    @DisplayName("Cursisten zien alleen BHV/EHBO-cursussen, geen ontruimingsoefeningen")
    void getAvailableCoursesForStudents() {
        Course ehbo = new Course();
        ehbo.setId(11L);
        ehbo.setType(TrainingType.EHBO);

        when(courseRepository.findAll()).thenReturn(List.of(bhvCourse, ehbo, evacuationCourse));

        var result = courseService.getAvailableCoursesForStudents();

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(c -> c.getType() != TrainingType.ONTRUIMINGSOEFENING);
    }

    // ============================================================
    // APPLY TRAINING TYPE LOGIC
    // ============================================================
    @Test
    @DisplayName("applyTrainingTypeLogic() zet juiste velden voor BHV en EHBO")
    void applyTrainingTypeLogic_BHV_EHBO() {
        Course ehboCourse = new Course();
        ehboCourse.setType(TrainingType.EHBO);
        ehboCourse.setMaxParticipants(null);

        courseService.applyTrainingTypeLogic(ehboCourse);

        assertThat(ehboCourse.isReportRequired()).isFalse();
        assertThat(ehboCourse.getMaxParticipants()).isEqualTo(10);
        assertThat(ehboCourse.getPhase()).isNull();
    }

    // --- Evacuation-specific logic ---
    @Test
    @DisplayName("applyTrainingTypeLogic() schakelt verslagverplichting uit bij Tabletop-fase")
    void applyTrainingTypeLogic_Evacuation_Tabletop() {
        evacuationCourse.setPhase(EvacuationPhase.TABLETOP);
        courseService.applyTrainingTypeLogic(evacuationCourse);

        assertThat(evacuationCourse.isReportRequired()).isFalse();
    }

    @Test
    @DisplayName("applyTrainingTypeLogic() schakelt verslagverplichting aan bij Unannounced Evacuation")
    void applyTrainingTypeLogic_Evacuation_WithReport() {
        evacuationCourse.setPhase(EvacuationPhase.UNANNOUNCED_EVACUATION);
        courseService.applyTrainingTypeLogic(evacuationCourse);

        assertThat(evacuationCourse.isReportRequired()).isTrue();
        assertThat(evacuationCourse.getMaxParticipants()).isNull();
    }
}
