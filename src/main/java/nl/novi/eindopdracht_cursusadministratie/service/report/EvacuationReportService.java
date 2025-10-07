package nl.novi.eindopdracht_cursusadministratie.service.report;

import nl.novi.eindopdracht_cursusadministratie.helper.EvacuationHelper;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.report.EvacuationReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvacuationReportService {

    private final EvacuationReportRepository reportRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EvacuationReportService(
            EvacuationReportRepository reportRepository,
            CourseRepository courseRepository,
            UserRepository userRepository
    ) {
        this.reportRepository = reportRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    //  Alle verslagen ophalen (alleen admin)
    public List<EvacuationReport> getAllReports() {
        return reportRepository.findAll();
    }

    //  Alle verslagen van één cursus
    public List<EvacuationReport> getReportsByCourse(Long courseId) {
        return reportRepository.findByCourseId(courseId);
    }

    //  Verslagen die zichtbaar zijn voor cursisten
    public List<EvacuationReport> getVisibleReports(Long courseId) {
        return reportRepository.findByCourseIdAndVisibleForStudentsTrue(courseId);
    }

    //  Verslag ophalen op ID
    public EvacuationReport getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evacuatieverslag niet gevonden met ID: " + id));
    }

    //  Nieuw verslag aanmaken door trainer
    public EvacuationReport createReport(Long courseId, Long trainerId, EvacuationReport reportData) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Cursus niet gevonden met ID: " + courseId));

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer niet gevonden met ID: " + trainerId));

        // Alleen ontruimingsoefeningen mogen een verslag hebben
        if (course.getType() != TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalArgumentException("Verslagen kunnen alleen worden gemaakt voor ontruimingsoefeningen.");
        }

        EvacuationReport report = new EvacuationReport();
        report.setCourse(course);
        report.setCreatedBy(trainer);
        report.setPhase(reportData.getPhase());
        report.setEvacuationTimeMinutes(reportData.getEvacuationTimeMinutes());
        report.setBuildingSize(reportData.getBuildingSize());
        report.setObservations(reportData.getObservations());
        report.setImprovements(reportData.getImprovements());

        //  Automatische evaluatie genereren
        String advies = EvacuationHelper.generateEvaluationAdvice(
                reportData.getPhase(),
                reportData.getEvacuationTimeMinutes(),
                reportData.getBuildingSize()
        );

        //  Controleer of de oefening binnen richttijd viel
        boolean binnenTijd = EvacuationHelper.isWithinTimeLimit(
                reportData.getPhase(),
                reportData.getEvacuationTimeMinutes(),
                reportData.getBuildingSize()
        );

        if (!binnenTijd) {
            advies += " (Let op: evacuatie duurde langer dan de richttijd)";
        }

        report.setEvaluationAdvice(advies);
        report.setStatus(ReportStatus.PENDING);

        return reportRepository.save(report);
    }

    //  Verslag bijwerken zolang status = PENDING
    public EvacuationReport updateReport(Long id, EvacuationReport updatedData) {
        EvacuationReport report = getReportById(id);

        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalStateException("Verslag kan niet meer worden aangepast na goedkeuring of afkeuring.");
        }

        report.setPhase(updatedData.getPhase());
        report.setEvacuationTimeMinutes(updatedData.getEvacuationTimeMinutes());
        report.setBuildingSize(updatedData.getBuildingSize());
        report.setObservations(updatedData.getObservations());
        report.setImprovements(updatedData.getImprovements());

        //  Automatisch nieuw advies genereren
        String advies = EvacuationHelper.generateEvaluationAdvice(
                updatedData.getPhase(),
                updatedData.getEvacuationTimeMinutes(),
                updatedData.getBuildingSize()
        );

        boolean binnenTijd = EvacuationHelper.isWithinTimeLimit(
                updatedData.getPhase(),
                updatedData.getEvacuationTimeMinutes(),
                updatedData.getBuildingSize()
        );

        if (!binnenTijd) {
            advies += " (Let op: evacuatie duurde langer dan de richttijd)";
        }

        report.setEvaluationAdvice(advies);
        return reportRepository.save(report);
    }

    //  Verslag goedkeuren door admin
    public EvacuationReport approveReport(Long id, Long adminId) {
        EvacuationReport report = getReportById(id);
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Beheerder niet gevonden met ID: " + adminId));

        report.setStatus(ReportStatus.APPROVED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(true);

        return reportRepository.save(report);
    }

    //  Verslag afkeuren door admin
    public EvacuationReport rejectReport(Long id, Long adminId, String remarks) {
        EvacuationReport report = getReportById(id);
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Beheerder niet gevonden met ID: " + adminId));

        report.setStatus(ReportStatus.REJECTED);
        report.setApprovedBy(admin);
        report.setVisibleForStudents(false);
        report.setEvaluationAdvice("Afgekeurd: " + remarks);

        return reportRepository.save(report);
    }

    //  Verslag verwijderen (alleen admin)
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
