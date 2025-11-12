package nl.novi.eindopdracht_cursusadministratie.service.certificate;

import nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.CertificateNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.helper.MailHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.pdf.PdfCertificateService;
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

class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PdfCertificateService pdfService;
    @Mock
    private MailHelper mailHelper;

    @InjectMocks
    private CertificateService certificateService;

    private Course course;
    private Trainer trainer;
    private Cursist student;
    private Certificate certificate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Trainer
        trainer = new Trainer();
        trainer.setId(3L);
        trainer.setName("Trainer X");

        // Cursus
        course = new Course();
        course.setId(1L);
        course.setName("BHV Basiscursus");
        course.setType(TrainingType.BHV);
        course.setTrainer(trainer);
        course.setEndDate(LocalDate.now().minusDays(1));

        // Cursist (mock, zodat hasCompletedCourse() werkt)
        student = mock(Cursist.class);
        when(student.getId()).thenReturn(2L);
        when(student.getName()).thenReturn("Jan Jansen");
        when(student.getEmail()).thenReturn("jan@example.com");
        when(student.hasCompletedCourse(any(Course.class))).thenReturn(true);

        // Basis-certificaat
        certificate = new Certificate();
        certificate.setId(99L);
        certificate.setCourse(course);
        certificate.setStudent(student);
        certificate.setCertificateNumber("CERT-2025-001");
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpiryDate(LocalDate.now().plusYears(1));
        certificate.setIssuedBy("Trainer X");
    }

    // ============================================================
    // GENERATE CERTIFICATE – HAPPY PATH
    // ============================================================
    @Test
    @DisplayName("Certificaat wordt gegenereerd voor goedgekeurde cursist door juiste trainer")
    void generateCertificate_Success() {
        // Arrange
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(2L)).thenReturn(Optional.of((User) student)); // cast vereist
        when(userRepository.findById(3L)).thenReturn(Optional.of((User) trainer));
        when(certificateRepository.findByCourse_IdAndStudent_Id(1L, 2L))
                .thenReturn(Optional.empty());
        when(pdfService.generateCertificatePdf(any(Certificate.class)))
                .thenReturn("fake-pdf".getBytes());
        when(certificateRepository.save(any(Certificate.class)))
                .thenAnswer(invocation -> {
                    Certificate saved = invocation.getArgument(0);
                    saved.setId(999L);
                    return saved;
                });

        CertificateResponseDto result = certificateService.generateCertificate(1L, 2L, 3L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(999L);
        assertThat(result.studentName()).isEqualTo("Jan Jansen");
        assertThat(result.courseName()).isEqualTo("BHV Basiscursus");

        verify(pdfService).generateCertificatePdf(any(Certificate.class));
        verify(certificateRepository).save(any(Certificate.class));
    }

    // ============================================================
    // GENERATE CERTIFICATE – NIET-AFGEBROND
    // ============================================================
    @Test
    @DisplayName("Bij niet-afgeronde cursus wordt IllegalStateException gegooid")
    void generateCertificate_NotCompleted_Throws() {
        when(student.hasCompletedCourse(any(Course.class))).thenReturn(false);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(2L)).thenReturn(Optional.of((User) student));
        when(userRepository.findById(3L)).thenReturn(Optional.of((User) trainer));

        assertThatThrownBy(() -> certificateService.generateCertificate(1L, 2L, 3L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("nog niet afgerond");
    }

    // ============================================================
    // GET EXPIRED CERTIFICATES
    // ============================================================
    @Test
    @DisplayName("Service geeft verlopen certificaten correct terug")
    void getExpiredCertificates() {
        Certificate expired = new Certificate();
        expired.setId(1L);
        expired.setCourse(course);
        expired.setStudent(student);
        expired.setExpiryDate(LocalDate.now().minusDays(1));

        Certificate valid = new Certificate();
        valid.setId(2L);
        valid.setCourse(course);
        valid.setStudent(student);
        valid.setExpiryDate(LocalDate.now().plusDays(10));

        when(certificateRepository.findAll()).thenReturn(List.of(expired, valid));

        var result = certificateService.getExpiredCertificates();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
    }

    // ============================================================
    // GET CERTIFICATES EXPIRING SOON
    // ============================================================
    @Test
    @DisplayName("Service geeft certificaten terug die binnenkort verlopen")
    void getCertificatesExpiringSoon() {
        Certificate soon = new Certificate();
        soon.setId(3L);
        soon.setCourse(course);
        soon.setStudent(student);
        soon.setExpiryDate(LocalDate.now().plusDays(5));

        Certificate later = new Certificate();
        later.setId(4L);
        later.setCourse(course);
        later.setStudent(student);
        later.setExpiryDate(LocalDate.now().plusDays(30));

        when(certificateRepository.findAll()).thenReturn(List.of(soon, later));

        var result = certificateService.getCertificatesExpiringSoon(7);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(3L);
    }

    // ============================================================
    // GET CERTIFICATE PDF
    // ============================================================
    @Test
    @DisplayName("PDF wordt opgehaald als die aanwezig is")
    void getCertificatePdf_Existing() {
        byte[] pdfBytes = "existing-pdf".getBytes();
        certificate.setPdfData(pdfBytes);

        when(certificateRepository.findById(99L)).thenReturn(Optional.of(certificate));

        byte[] result = certificateService.getCertificatePdf(99L);

        assertThat(result).isEqualTo(pdfBytes);
        verify(pdfService, never()).generateCertificatePdf(any());
    }

    @Test
    @DisplayName("PDF wordt opnieuw gegenereerd als die ontbreekt")
    void getCertificatePdf_RegenerateIfMissing() {
        certificate.setPdfData(null);

        when(certificateRepository.findById(99L)).thenReturn(Optional.of(certificate));
        when(pdfService.generateCertificatePdf(any(Certificate.class)))
                .thenReturn("new-pdf".getBytes());
        when(certificateRepository.save(any(Certificate.class)))
                .thenReturn(certificate);

        byte[] result = certificateService.getCertificatePdf(99L);

        assertThat(result).isEqualTo("new-pdf".getBytes());
        verify(pdfService).generateCertificatePdf(any(Certificate.class));
        verify(certificateRepository).save(any(Certificate.class));
    }

    // ============================================================
    // EXCEPTION CASE
    // ============================================================
    @Test
    @DisplayName("Bij onbekend certificaat-ID wordt CertificateNotFoundException gegooid")
    void getCertificatePdf_NotFound_Throws() {
        when(certificateRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> certificateService.getCertificatePdf(999L))
                .isInstanceOf(CertificateNotFoundException.class)
                .hasMessageContaining("999");
    }
}
