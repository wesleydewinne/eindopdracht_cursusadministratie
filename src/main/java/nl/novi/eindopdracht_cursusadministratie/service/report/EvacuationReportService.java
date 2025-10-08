package nl.novi.eindopdracht_cursusadministratie.service.report;

import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.EvacuationReportNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.TrainerNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.UserNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.report.EvacuationReportRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;
import static nl.novi.eindopdracht_cursusadministratie.helper.EvacuationReportHelper.applyReportDetails;

@Service
public class EvacuationReportService {

    private final EvacuationReportRepository reportRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EvacuationReportService(EvacuationReportRepository reportRepository,
                                   CourseRepository courseRepository,
                                   UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // BASIS CRUD
    // ============================================================

    public List<EvacuationReport> getAllReports() {
        return reportRepository.findAll();
    }

    public List<EvacuationReport> getReportsByCourse(Long courseId) {
        return reportRepository.findByCourseId(courseId);
    }

    public List<EvacuationReport> getVisibleReports(Long courseId) {
        return reportRepository.findByCourseIdAndVisibleForStudentsTrue(courseId);
    }

    public EvacuationReport getReportById(Long id) {
        return findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
    }

    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EvacuationReportNotFoundException(id);
        }
        reportRepository.deleteById(id);
    }

    // ============================================================
    // CREATE & UPDATE
    // ============================================================

    public EvacuationReport createReport(Long courseId, Long trainerId, EvacuationReport reportData) {
        Course course = findEntityById(courseId, courseRepository, new CourseNotFoundException(courseId));
        User trainer = findEntityById(trainerId, userRepository, new TrainerNotFoundException(trainerId));

        // Alleen ontruimingsoefeningen mogen een verslag hebben
        if (course.getType() != TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalStateException("Verslagen kunnen alleen worden gemaakt voor ontruimingsoefeningen.");
        }

        EvacuationReport report = new EvacuationReport();
        report.setCourse(course);
        report.setCreatedBy(trainer);
        report.setStatus(ReportStatus.PENDING);

        applyReportDetails(report, reportData);

        return reportRepository.save(report);
    }

    public EvacuationReport updateReport(Long id, EvacuationReport updatedData) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalStateException("Verslag kan niet meer worden aangepast na goedkeuring of afkeuring.");
        }

        applyReportDetails(report, updatedData);

        return reportRepository.save(report);
    }

    // ============================================================
    // ADMIN ACTIES (goedkeuren / afkeuren)
    // ============================================================

    public EvacuationReport approveReport(Long id, Long adminId) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(true);

        return reportRepository.save(report);
    }

    public EvacuationReport rejectReport(Long id, Long adminId, String remarks) {
        EvacuationReport report = findEntityById(id, reportRepository, new EvacuationReportNotFoundException(id));
        User admin = findEntityById(adminId, userRepository, new UserNotFoundException(adminId));

        report.setStatus(ReportStatus.REJECTED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(false);
        report.setEvaluationAdvice("Afgekeurd: " + remarks);

        return reportRepository.save(report);
    }
}
