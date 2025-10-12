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
    // CRUD + ZICHTBAARHEID
    // ============================================================

    /** Alle verslagen ophalen (alleen admin) */
    public List<EvacuationReport> getAllReports() {
        return reportRepository.findAll();
    }

    /** Alle verslagen van een specifieke cursus ophalen */
    public List<EvacuationReport> getReportsByCourse(Long courseId) {
        return reportRepository.findByCourseId(courseId);
    }

    /** Alleen goedgekeurde verslagen die zichtbaar zijn voor cursisten */
    public List<EvacuationReport> getVisibleReports(Long courseId) {
        return reportRepository.findByCourseIdAndVisibleForStudentsTrue(courseId);
    }

    /** Specifiek verslag ophalen op ID */
    public EvacuationReport getReportById(Long id) {
        return findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
    }

    /** Verslag verwijderen (alleen admin) */
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EvacuationReportNotFoundException(id);
        }
        reportRepository.deleteById(id);
    }

    // ============================================================
    // CREATE & UPDATE
    // ============================================================

    /** Nieuw verslag aanmaken door trainer */
    public EvacuationReport createReport(Long courseId, Long trainerId, EvacuationReport reportData) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User trainer = findEntityById(trainerId, userRepository, new TrainerNotFoundException(trainerId));

        if (course.getType() != TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("Verslagen kunnen alleen worden gemaakt voor ontruimingsoefeningen.");
        }

        EvacuationReport report = new EvacuationReport();
        report.setCourse(course);
        report.setCreatedBy(trainer);
        report.setStatus(ReportStatus.PENDING);

        applyReportDetails(report, reportData);

        report.setPdfData(pdfService.generateReportPdf(report));

        return reportRepository.save(report);
    }

    /** Bestaand verslag bijwerken (zolang het nog in PENDING staat) */
    public EvacuationReport updateReport(Long id, EvacuationReport updatedData) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalStateException(
                    "Verslag kan niet meer worden aangepast na goedkeuring of afkeuring."
            );
        }

        applyReportDetails(report, updatedData);
        report.setPdfData(pdfService.generateReportPdf(report));

        return reportRepository.save(report);
    }

    // ============================================================
    // ADMIN ACTIES (GOEDKEUREN / AFKEUREN)
    // ============================================================

    /** Verslag goedkeuren door admin */
    public EvacuationReport approveReport(Long id, Long adminId) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(true);

        report.setPdfData(pdfService.generateReportPdf(report));
        return reportRepository.save(report);
    }

    /** Verslag afkeuren door admin */
    public EvacuationReport rejectReport(Long id, Long adminId, String remarks) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.REJECTED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(false);
        report.setEvaluationAdvice("Afgekeurd: " + remarks);

        report.setPdfData(pdfService.generateReportPdf(report));
        return reportRepository.save(report);
    }

    // ============================================================
    // PDF DOWNLOAD
    // ============================================================

    /**
     * Haalt de PDF van een ontruimingsverslag op.
     * Als er nog geen PDF in de database staat, wordt deze automatisch gegenereerd.
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