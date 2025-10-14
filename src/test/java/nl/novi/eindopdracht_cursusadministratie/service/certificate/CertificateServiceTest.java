package nl.novi.eindopdracht_cursusadministratie.service.certificate;

import nl.novi.eindopdracht_cursusadministratie.exception.CertificateNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.helper.CertificateNumberHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.DateHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.pdf.PdfCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private PdfCertificateService pdfCertificateService;

    @InjectMocks
    private CertificateService certificateService;

    private Course course;
    private Cursist cursist;
    private Certificate certificate;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setName("BHV Basis");
        course.setType(TrainingType.BHV);

        cursist = new Cursist();
        cursist.setId(1L);
        cursist.setName("Piet Cursist");
        cursist.setEmail("piet@bhv.nl");

        certificate = new Certificate();
        certificate.setId(1L);
        certificate.setCertificateNumber("CERT-001");
        certificate.setCourse(course);
        certificate.setStudent(cursist);
    }

    // =============================================================
    // TESTS
    // =============================================================

    @Test
    void shouldReturnAllCertificates() {
        // Arrange
        when(certificateRepository.findAll()).thenReturn(List.of(certificate));

        // Act
        List<Certificate> result = certificateService.getAllCertificates();

        // Assert
        assertEquals(1, result.size());
        assertEquals("CERT-001", result.get(0).getCertificateNumber());
        verify(certificateRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnCertificateById_whenExists() {
        // Arrange
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(certificate));

        // Act
        Certificate found = certificateService.getCertificateById(1L);

        // Assert
        assertNotNull(found);
        assertEquals("CERT-001", found.getCertificateNumber());
        verify(certificateRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowException_whenCertificateNotFound() {
        // Arrange
        when(certificateRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CertificateNotFoundException.class, () -> certificateService.getCertificateById(99L));
    }

    @Test
    void shouldCreateCertificate_whenCursistCompletedCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(1L)).thenReturn(Optional.of(cursist));
        when(pdfCertificateService.generateCertificatePdf(any())).thenReturn("PDFDATA".getBytes());
        when(certificateRepository.save(any(Certificate.class))).thenAnswer(i -> i.getArguments()[0]);

        // Mock dat de cursist de cursus heeft voltooid
        Cursist spyCursist = spy(cursist);
        doReturn(true).when(spyCursist).hasCompletedCourse(course);
        when(userRepository.findById(1L)).thenReturn(Optional.of(spyCursist));

        // Act
        Certificate result = certificateService.createCertificate(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("BHV Basis", result.getCourse().getName());
        assertNotNull(result.getCertificateNumber());
        verify(certificateRepository, times(1)).save(any(Certificate.class));
    }

    @Test
    void shouldThrowException_whenCursistNotCompletedCourse() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(1L)).thenReturn(Optional.of(cursist));

        // Mock dat cursist de cursus niet voltooid heeft
        Cursist spyCursist = spy(cursist);
        doReturn(false).when(spyCursist).hasCompletedCourse(course);
        when(userRepository.findById(1L)).thenReturn(Optional.of(spyCursist));

        // Act + Assert
        assertThrows(CursistNotFoundException.class, () -> certificateService.createCertificate(1L, 1L));
    }

    @Test
    void shouldThrowException_whenCourseNotFound() {
        // Arrange
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CourseNotFoundException.class, () -> certificateService.createCertificate(1L, 99L));
    }

    @Test
    void shouldDeleteCertificate_whenExists() {
        // Arrange
        when(certificateRepository.existsById(1L)).thenReturn(true);

        // Act
        certificateService.deleteCertificate(1L);

        // Assert
        verify(certificateRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistingCertificate() {
        // Arrange
        when(certificateRepository.existsById(5L)).thenReturn(false);

        // Act + Assert
        assertThrows(CertificateNotFoundException.class, () -> certificateService.deleteCertificate(5L));
    }
}
