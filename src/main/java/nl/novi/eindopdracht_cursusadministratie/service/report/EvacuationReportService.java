package nl.novi.eindopdracht_cursusadministratie.service.report;

import jakarta.transaction.Transactional;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
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
    // ================= ALGEMEEN OPVRAGEN ========================
    // ============================================================

    public List<EvacuationReport> getAllReports() {
        return reportRepository.findAll();
    }

    public EvacuationReport getReportById(Long id) {
        return findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
    }

    public List<EvacuationReport> getReportsByTrainer(Long trainerId) {
        return reportRepository.findByCreatedBy_Id(trainerId);
    }

    public List<EvacuationReport> getVisibleReportsByLocation(Long courseId, Long locationId) {
        return reportRepository.findByCourseIdAndLocationAndApprovedVisible(courseId, locationId);
    }


    // ============================================================
    // ================= TRAINER ACTIES ===========================
    // ============================================================

    public EvacuationReport createReport(Long courseId, Long trainerId, EvacuationReport reportData) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User trainer = findEntityById(trainerId, userRepository, new TrainerNotFoundException(trainerId));

        if (!course.isEvacuationTraining()) {
            throw new IllegalStateException("Verslagen kunnen alleen worden gemaakt voor ontruimingsoefeningen.");
        }

        if (!course.isReportRequired()) {
            throw new IllegalStateException("Deze ontruimingoefening vereist geen verslag (fase: "
                    + course.getPhase() + ").");
        }

        if (course.getTrainer() == null || !course.getTrainer().getId().equals(trainerId)) {
            throw new UnauthorizedAccessException("Je kunt alleen een verslag aanmaken voor je eigen cursus.");
        }

        if (reportRepository.findFirstByCourseId(courseId).isPresent()) {
            throw new IllegalStateException("Er bestaat al een verslag voor deze ontruimingsoefening.");
        }

        EvacuationReport report = new EvacuationReport();
        report.setCourse(course);
        report.setCreatedBy(trainer);
        report.setStatus(ReportStatus.PENDING);
        report.setVisibleForStudents(false);

        applyReportDetails(report, reportData);


        course.setEvacuationReport(report);

        return reportRepository.save(report);
    }

    public EvacuationReport updateReport(Long id, EvacuationReport updatedData, User editor) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));

        if (editor == null) {
            throw new UnauthorizedAccessException("Ingelogde gebruiker kon niet worden bepaald.");
        }

        boolean isTrainer = editor.getRole().name().equalsIgnoreCase("TRAINER");

        if (isTrainer) {
            if (report.getCreatedBy() == null || !report.getCreatedBy().getId().equals(editor.getId())) {
                throw new UnauthorizedAccessException("Je kunt alleen je eigen rapporten wijzigen.");
            }
            if (report.getStatus() == ReportStatus.APPROVED) {
                throw new IllegalStateException("Goedgekeurde verslagen kunnen niet meer worden aangepast.");
            }
        }

        applyReportDetails(report, updatedData);

        if (report.getStatus() == ReportStatus.REJECTED) {
            report.setStatus(ReportStatus.PENDING);
            report.setApprovedBy(null);
            report.setVisibleForStudents(false);
        }

        report.setPdfData(null);

        return reportRepository.save(report);
    }


    // ============================================================
    // ================= ADMIN ACTIES =============================
    // ============================================================

    public EvacuationReport approveReport(Long id, Long adminId) {
        EvacuationReport report = getReportById(id);
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(true);
        report.setPdfData(pdfService.generateReportPdf(report));

        return reportRepository.save(report);
    }

    public EvacuationReport rejectReport(Long id, Long adminId, String remarks) {
        EvacuationReport report = getReportById(id);
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.REJECTED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(false);
        report.setEvaluationAdvice("Afgekeurd door admin: " + remarks);
        report.setPdfData(null);

        return reportRepository.save(report);
    }
    // ============================================================
    // ================= PDF ======================================
    // ============================================================

    public byte[] getReportPdf(Long reportId) {
        EvacuationReport report = getReportById(reportId);

        if (report.getStatus() != ReportStatus.APPROVED) {
            throw new IllegalStateException("Alleen goedgekeurde verslagen hebben een PDF-bestand.");
        }

        if (report.getPdfData() == null || report.getPdfData().length == 0) {
            report.setPdfData(pdfService.generateReportPdf(report));
            reportRepository.save(report);
        }

        return report.getPdfData();
    }


    // ============================================================
    // ================= DELETE ===================================
    // ============================================================

    @Transactional
    public boolean deleteReportById(Long id) {
        EvacuationReport report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return false;
        }

        Course course = report.getCourse();
        if (course != null) {
            course.setEvacuationReport(null);
            report.setCourse(null);
        }

        reportRepository.delete(report);
        reportRepository.flush();

        System.out.println("✅ Ontruimingsverslag met ID " + id + " is verwijderd uit de database.");
        return true;
    }
}
