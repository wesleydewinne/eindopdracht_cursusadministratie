package nl.novi.eindopdracht_cursusadministratie.repository.report;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvacuationReportRepository extends JpaRepository<EvacuationReport, Long> {

    // Alle verslagen van een specifieke trainer ophalen
    List<EvacuationReport> findByCreatedById(Long trainerId);

    // Alle verslagen van een specifieke cursus ophalen
    List<EvacuationReport> findByCourseId(Long courseId);

    // Alleen verslagen die zichtbaar zijn voor cursisten
    List<EvacuationReport> findByCourseIdAndVisibleForStudentsTrue(Long courseId);

    // Verslagen filteren op status (bijv. PENDING, APPROVED, REJECTED)
    List<EvacuationReport> findByStatus(ReportStatus status);
}
