package nl.novi.eindopdracht_cursusadministratie.service.user;

import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseCompletionSummaryDto;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationStatusResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.helper.MailHelper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Mock private TrainerRepository trainerRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private RegistrationRepository registrationRepository;
    @Mock private CertificateRepository certificateRepository;
    @Mock private UserRepository userRepository;
    @Mock private MailHelper mailHelper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private Cursist student;
    private Course course;
    private Registration registration;

    // ============================================================
    // SETUP + SECURITY MOCK
    // ============================================================
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setName("Trainer X");
        trainer.setEmail("trainer@example.com");
        trainer.setRole(Role.TRAINER);

        student = new Cursist();
        student.setId(2L);
        student.setName("Jan Jansen");
        student.setEmail("jan@example.com");

        course = new Course();
        course.setId(3L);
        course.setName("BHV Basiscursus");
        course.setTrainer(trainer);
        course.setType(TrainingType.BHV);
        course.setRegistrations(new ArrayList<>());

        registration = new Registration();
        registration.setId(10L);
        registration.setCourse(course);
        registration.setStudent(student);
        registration.setStatus(RegistrationStatus.PENDING);
        registration.setPresent(true);
        course.getRegistrations().add(registration);

        var auth = new UsernamePasswordAuthenticationToken(
                trainer.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_TRAINER"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // ============================================================
    // GET TRAINER BY ID
    // ============================================================
    @Test
    @DisplayName("Trainer wordt correct opgehaald op basis van ID")
    void getTrainerById() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        var result = trainerService.getTrainerById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Trainer X");
        verify(trainerRepository).findById(1L);
    }

    // ============================================================
    // GET COURSES BY TRAINER
    // ============================================================
    @Test
    @DisplayName("Trainer kan overzicht van eigen cursussen ophalen")
    void getCoursesByTrainer() {
        when(courseRepository.findByTrainer_Id(1L)).thenReturn(List.of(course));

        List<CourseTrainerResponseDto> result = trainerService.getCoursesByTrainer(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("BHV Basiscursus");
        verify(courseRepository).findByTrainer_Id(1L);
    }

    // ============================================================
    // COMPLETE COURSE
    // ============================================================
    @Test
    @DisplayName("Cursus wordt correct afgerond en absenties gemarkeerd")
    void completeCourse() {
        registration.setStatus(RegistrationStatus.APPROVED);

        Registration absent = new Registration();
        absent.setStudent(student);
        absent.setPresent(false);
        absent.setStatus(RegistrationStatus.PENDING);
        course.getRegistrations().add(absent);

        when(courseRepository.findById(3L)).thenReturn(Optional.of(course));

        CourseCompletionSummaryDto result = trainerService.completeCourse(3L);

        assertThat(result.approved()).contains("Jan Jansen");
        assertThat(result.absent()).contains("Jan Jansen");
        verify(registrationRepository).saveAll(anyList());
    }

    // ============================================================
    // MARK ATTENDANCE
    // ============================================================
    @Test
    @DisplayName("Trainer kan aanwezigheid markeren voor cursist")
    void markAttendanceForCourse() {
        when(registrationRepository.findById(10L)).thenReturn(Optional.of(registration));

        RegistrationStatusResponseDto result = trainerService.markAttendanceForCourse(3L, 10L, true);

        assertThat(result.isPresent()).isTrue();
        verify(registrationRepository).save(any(Registration.class));
    }

    // ============================================================
    // UPDATE STATUS
    // ============================================================
    @Test
    @DisplayName("Trainer kan status bijwerken van cursist")
    void updateRegistrationStatusForCourse() {
        when(registrationRepository.findById(10L)).thenReturn(Optional.of(registration));
        when(certificateRepository.findByCourse_IdAndStudent_Id(3L, 2L))
                .thenReturn(Optional.empty());
        when(certificateRepository.save(any(Certificate.class)))
                .thenAnswer(i -> i.getArgument(0));

        RegistrationStatusResponseDto result = trainerService.updateRegistrationStatusForCourse(
                3L, 10L, RegistrationStatus.APPROVED
        );

        assertThat(result.getStatus()).isEqualTo(RegistrationStatus.APPROVED);
        verify(certificateRepository).save(any(Certificate.class));
    }

    // ============================================================
    // GENERATE CERTIFICATE
    // ============================================================
    @Test
    @DisplayName("Trainer kan handmatig certificaat genereren")
    void generateCertificate() {
        when(courseRepository.findById(3L)).thenReturn(Optional.of(course));
        when(userRepository.findById(2L)).thenReturn(Optional.of(student));
        when(certificateRepository.save(any(Certificate.class))).thenAnswer(i -> i.getArgument(0));

        Certificate result = trainerService.generateCertificate(3L, 2L, "Trainer X");

        assertThat(result.getStudent().getName()).isEqualTo("Jan Jansen");
        assertThat(result.getCourse().getName()).isEqualTo("BHV Basiscursus");
        verify(mailHelper).sendCertificateNotification(
                eq("jan@example.com"), eq("Jan Jansen"), eq("BHV Basiscursus"),
                anyString(), anyString(), eq("Trainer X")
        );
    }

    // ============================================================
    // EXCEPTION CASE
    // ============================================================
    @Test
    @DisplayName("Bij onbekende cursus-ID wordt CourseNotFoundException gegooid")
    void completeCourse_CourseNotFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> trainerService.completeCourse(99L))
                .isInstanceOf(CourseNotFoundException.class);
    }
}
