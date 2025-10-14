package nl.novi.eindopdracht_cursusadministratie.service.user;

import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.helper.MailHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock private TrainerRepository trainerRepository;
    @Mock private CourseRepository courseRepository;
    @Mock private RegistrationRepository registrationRepository;
    @Mock private CertificateRepository certificateRepository;
    @Mock private UserRepository userRepository;
    @Mock private MailHelper mailHelper;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private Course course;
    private Registration registration;
    private User student;

    @BeforeEach
    void setUp() {
        trainer = new Trainer();
        trainer.setId(10L);
        trainer.setName("Jan Trainer");
        trainer.setEmail("jan.trainer@bhvtraining.nl");

        course = new Course();
        course.setId(1L);
        course.setName("EHBO Basis");
        course.setType(TrainingType.EHBO);
        course.setTrainer(trainer);

        registration = new Registration();
        registration.setId(1L);
        registration.setCourse(course);
        registration.setPresent(false);

        student = new User();
        student.setId(100L);
        student.setName("Piet Student");
        student.setEmail("piet@student.nl");

        TestingAuthenticationToken token =
                new TestingAuthenticationToken(trainer.getEmail(), "password");
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);

        lenient().when(userRepository.findByEmail(trainer.getEmail()))
                .thenReturn(Optional.of(trainer));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // =============================================================
    // TESTS
    // =============================================================

    @Test
    void shouldReturnTrainer_whenFoundById() {
        when(trainerRepository.findById(10L)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getTrainerById(10L);

        assertNotNull(result);
        assertEquals("Jan Trainer", result.getName());
        verify(trainerRepository).findById(10L);
    }

    @Test
    void shouldUpdateTrainerDetailsSuccessfully() {
        Trainer updated = new Trainer();
        updated.setName("Nieuwe Naam");
        updated.setEmail("nieuw@bhvtraining.nl");
        updated.setExpertise("EHBO");

        when(trainerRepository.findById(10L)).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenAnswer(i -> i.getArguments()[0]);

        Trainer result = trainerService.updateTrainer(10L, updated);

        assertEquals("Nieuwe Naam", result.getName());
        assertEquals("nieuw@bhvtraining.nl", result.getEmail());
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    void shouldThrowAccessDenied_whenTrainerNotOwnerOfCourse() {
        Trainer otherTrainer = new Trainer();
        otherTrainer.setId(99L);

        Course otherCourse = new Course();
        otherCourse.setId(2L);
        otherCourse.setTrainer(otherTrainer);
        registration.setCourse(otherCourse);

        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));

        TestingAuthenticationToken token =
                new TestingAuthenticationToken("someone@bhvtraining.nl", "password");
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);

        lenient().when(userRepository.findByEmail("someone@bhvtraining.nl"))
                .thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class,
                () -> trainerService.markAttendance(1L, true));
    }

    @Test
    void shouldMarkAttendanceSuccessfully_whenTrainerIsOwner() {
        when(registrationRepository.findById(1L)).thenReturn(Optional.of(registration));
        when(registrationRepository.save(any(Registration.class))).thenAnswer(i -> i.getArguments()[0]);

        Registration result = trainerService.markAttendance(1L, true);

        assertTrue(result.isPresent());
        verify(registrationRepository).save(any(Registration.class));
    }

    @Test
    void shouldGenerateCertificateAndSendEmail() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(100L)).thenReturn(Optional.of(student));
        when(certificateRepository.save(any(Certificate.class))).thenAnswer(i -> i.getArguments()[0]);

        Certificate result = trainerService.generateCertificate(1L, 100L, "BHV Training");

        assertNotNull(result.getCertificateNumber());
        assertEquals("EHBO Basis", result.getCourse().getName());
        assertEquals("BHV Training", result.getIssuedBy());
        verify(mailHelper).sendCertificateNotification(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void shouldThrowException_whenCourseTypeIsEvacuation() {
        course.setType(TrainingType.ONTRUIMINGSOEFENING);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(100L)).thenReturn(Optional.of(student));

        assertThrows(IllegalStateException.class,
                () -> trainerService.generateCertificate(1L, 100L, "BHV Training"));
    }

    @Test
    void shouldGetCertificatesByTrainer_whenAuthorized() {
        when(certificateRepository.findByCourse_Trainer_Id(10L))
                .thenReturn(List.of(new Certificate()));

        var result = trainerService.getCertificatesByTrainer(10L);

        assertEquals(1, result.size());
        verify(certificateRepository).findByCourse_Trainer_Id(10L);
    }
}
