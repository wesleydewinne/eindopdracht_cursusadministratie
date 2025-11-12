package nl.novi.eindopdracht_cursusadministratie.service.certificate;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PdfCertificateService pdfService;

    // ============================================================
    // ALGEMENE OPVRAGINGEN
    // ============================================================

    /** Admin of trainer haalt alle certificaten op */
    public List<CertificateResponseDto> getAllCertificates() {
        return certificateRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Haalt een specifiek certificaat op als DTO */
    public CertificateResponseDto getCertificateById(Long id) {
        Certificate cert = findEntityById(id, certificateRepository, new CertificateNotFoundException(id));
        return toResponseDto(cert);
    }

    /** Haalt de onderliggende entiteit zelf op (voor securitycontrole in controller) */
    public Certificate getCertificateEntity(Long id) {
        return findEntityById(id, certificateRepository, new CertificateNotFoundException(id));
    }

    /** Haalt alle certificaten van één trainer op */
    public List<CertificateResponseDto> getCertificatesByTrainer(Long trainerId) {
        return certificateRepository.findByCourse_Trainer_Id(trainerId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Haalt alle certificaten van één cursist op (fetch-variant incl. namen) */
    public List<CertificateResponseDto> getCertificatesByCursist(Long studentId) {
        return certificateRepository.findByStudent_IdFetchAll(studentId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    /** Haalt certificaten op via cursist-email (voor endpoint /mine) */
    public List<CertificateResponseDto> getCertificatesByCursistEmail(String email) {
        return certificateRepository.findByStudent_EmailIgnoreCase(email).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // ============================================================
    // TRAINER ACTIES
    // ============================================================

    /**
     * Trainer maakt een nieuw certificaat aan voor een cursist.
     * - Alleen toegestaan voor cursussen van de trainer zelf.
     * - Alleen als de cursist de training heeft voltooid.
     * - Automatische PDF-generatie en vervaldatum.
     */
    public CertificateResponseDto generateCertificate(Long courseId, Long studentId, Long trainerId) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User trainer = findEntityById(trainerId, userRepository, new TrainerNotFoundException(trainerId));
        User studentUser = findEntityById(studentId, userRepository, new CursistNotFoundException(studentId));

        if (!(studentUser instanceof Cursist cursist)) {
            throw new IllegalArgumentException("User met ID " + studentId + " is geen Cursist.");
        }

        if (course.getTrainer() == null || !course.getTrainer().getId().equals(trainerId)) {
            throw new UnauthorizedAccessException("Je kunt alleen certificaten genereren voor je eigen cursussen.");
        }

        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("Er wordt geen certificaat uitgegeven voor ontruimingsoefeningen.");
        }

        if (!cursist.hasCompletedCourse(course)) {
            throw new IllegalStateException(
                    "Cursist " + cursist.getName() + " heeft de cursus '" + course.getName() + "' nog niet afgerond."
            );
        }

        boolean alreadyExists = !certificateRepository
                .findByCourse_IdAndStudent_Id(courseId, studentId)
                .isEmpty();
        if (alreadyExists) {
            throw new IllegalStateException("Er bestaat al een certificaat voor deze cursist binnen deze cursus.");
        }

        Certificate certificate = new Certificate();
        certificate.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpiryDate(DateHelper.calculateExpiryDateOrIssue(
                certificate.getIssueDate(), course.getType()));
        certificate.setIssuedBy(trainer.getName());
        certificate.setStudent(cursist);
        certificate.setCourse(course);
        certificate.setPdfData(pdfService.generateCertificatePdf(certificate));

        Certificate saved = certificateRepository.save(certificate);
        return toResponseDto(saved);
    }

    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    public void deleteCertificate(Long id) {
        Certificate cert = findEntityById(id, certificateRepository, new CertificateNotFoundException(id));
        certificateRepository.delete(cert);
    }

    public List<CertificateResponseDto> getExpiredCertificates() {
        return certificateRepository.findAll().stream()
                .filter(cert -> DateHelper.isCertificateExpired(cert.getExpiryDate()))
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }


    public List<CertificateResponseDto> getCertificatesExpiringSoon(int daysAhead) {
        return certificateRepository.findAll().stream()
                .filter(cert -> cert.getExpiryDate() != null)
                .filter(cert -> {
                    long days = DateHelper.daysUntilExpiry(cert.getExpiryDate());
                    return days >= 0 && days <= daysAhead;
                })
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // ============================================================
    // PDF DOWNLOAD
    // ============================================================

    public byte[] getCertificatePdf(Long certificateId) {
        Certificate cert = findEntityById(certificateId, certificateRepository, new CertificateNotFoundException(certificateId));

        if (cert.getPdfData() == null || cert.getPdfData().length == 0) {
            cert.setPdfData(pdfService.generateCertificatePdf(cert));
            certificateRepository.save(cert);
        }

        return cert.getPdfData();
    }

    // ============================================================
    // MAPPER
    // ============================================================

    private @NotNull CertificateResponseDto toResponseDto(@NotNull Certificate cert) {
        String courseName = cert.getCourse() != null ? cert.getCourse().getName() : "-";
        String studentName = cert.getStudent() != null ? cert.getStudent().getName() : "-";

        return new CertificateResponseDto(
                cert.getId(),
                cert.getCertificateNumber(),
                courseName,
                studentName,
                cert.getIssueDate(),
                cert.getExpiryDate(),
                cert.getIssuedBy()
        );
    }
}
