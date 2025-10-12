package nl.novi.eindopdracht_cursusadministratie.service.report;

import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.report.EvacuationReportRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.pdf.PdfEvacuationReportService;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;
import static nl.novi.eindopdracht_cursusadministratie.helper.EvacuationReportHelper.applyReportDetails;

/**
 * Service voor het beheren van ontruimingsverslagen.
 * Alleen ontruimingsoefeningen (type ONTRUIMINGSOEFENING) kunnen verslagen bevatten.
 * Een verslag mag alleen worden aangemaakt als de cursus reportRequired = true heeft.
 */
@Service
public class EvacuationReportService {

    private final EvacuationReportRepository reportRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PdfEvacuationReportService pdfService;

    public EvacuationReportService(
            EvacuationReportRepository reportRepository,
            CourseRepository courseRepository,
            UserRepository userRepository,
            PdfEvacuationReportService pdfService
    ) {
        this.reportRepository = reportRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.pdfService = pdfService;
    }

    // ============================================================
    // ALGEMENE CRUD + OPVRAGEN
    // ============================================================

    /** Haalt alle verslagen op (alleen admin) */
    public List<EvacuationReport> getAllReports() {
        return reportRepository.findAll();
    }

    /** Haalt alle verslagen van een specifieke cursus op */
    public List<EvacuationReport> getReportsByCourse(Long courseId) {
        return reportRepository.findByCourseId(courseId);
    }

    /** Haalt alleen goedgekeurde verslagen op die zichtbaar zijn voor cursisten */
    public List<EvacuationReport> getVisibleReports(Long courseId) {
        return reportRepository.findByCourseIdAndVisibleForStudentsTrue(courseId);
    }

    /** Haalt een specifiek verslag op via ID */
    public EvacuationReport getReportById(Long id) {
        return findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
    }

    /** Verwijdert een verslag op ID (alleen admin) */
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EvacuationReportNotFoundException(id);
        }
        reportRepository.deleteById(id);
    }

    // ============================================================
    // TRAINER ACTIES
    // ============================================================

    /**
     * Maakt een nieuw verslag aan voor een ontruimingsoefening.
     * Alleen toegestaan als de cursus type = ONTRUIMINGSOEFENING en reportRequired = true heeft.
     */
    public EvacuationReport createReport(Long courseId, Long trainerId, EvacuationReport reportData) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User trainer = findEntityById(trainerId, userRepository, new TrainerNotFoundException(trainerId));

        // Alleen ontruimingsoefeningen mogen een verslag krijgen
        if (course.getType() != TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("Verslagen kunnen alleen worden gemaakt voor ontruimingsoefeningen.");
        }

        // Check of een verslag verplicht is
        if (!course.isReportRequired()) {
            throw new IllegalStateException("Deze ontruimingsoefening vereist geen verslag (fase: " +
                    course.getPhase() + ").");
        }

        // Controleer of er al een verslag bestaat voor deze cursus
        List<EvacuationReport> existingReports = reportRepository.findByCourseId(courseId);
        if (!existingReports.isEmpty()) {
            throw new IllegalStateException("Er bestaat al een verslag voor deze ontruimingsoefening.");
        }

        EvacuationReport report = new EvacuationReport();
        report.setCourse(course);
        report.setCreatedBy(trainer);
        report.setStatus(ReportStatus.PENDING);
        report.setVisibleForStudents(false);

        applyReportDetails(report, reportData);
        report.setPdfData(pdfService.generateReportPdf(report));

        return reportRepository.save(report);
    }

    /**
     * Werkt een bestaand verslag bij zolang de status = PENDING.
     */
    public EvacuationReport updateReport(Long id, EvacuationReport updatedData) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalStateException("Verslag kan niet meer worden aangepast na goedkeuring of afkeuring.");
        }

        applyReportDetails(report, updatedData);
        report.setPdfData(pdfService.generateReportPdf(report));

        return reportRepository.save(report);
    }

    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    /**
     * Keurt een verslag goed.
     * Zet status op APPROVED, maakt zichtbaar voor cursisten en genereert nieuwe PDF.
     */
    public EvacuationReport approveReport(Long id, Long adminId) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(true);

        // Genereer bijgewerkte PDF met goedkeuringsinfo
        report.setPdfData(pdfService.generateReportPdf(report));
        return reportRepository.save(report);
    }

    /**
     * Keurt een verslag af.
     * Zet status op REJECTED en maakt onzichtbaar voor cursisten.
     */
    public EvacuationReport rejectReport(Long id, Long adminId, String remarks) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.REJECTED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(false);
        report.setEvaluationAdvice("Afgekeurd door admin: " + remarks);

        report.setPdfData(pdfService.generateReportPdf(report));
        return reportRepository.save(report);
    }

    // ============================================================
    // PDF DOWNLOAD
    // ============================================================

    /**
     * Haalt de PDF van een verslag op.
     * Als de PDF nog niet is gegenereerd, wordt deze automatisch aangemaakt.
     */
    public byte[] getReportPdf(Long reportId) {
        EvacuationReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EvacuationReportNotFoundException(reportId));

        if (report.getPdfData() != null && report.getPdfData().length > 0) {
            return report.getPdfData();
        }

        byte[] pdf = pdfService.generateReportPdf(report);
        report.setPdfData(pdf);
        reportRepository.save(report);

        return pdf;
    }
}
