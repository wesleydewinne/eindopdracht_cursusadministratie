package nl.novi.eindopdracht_cursusadministratie.repository.report;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvacuationReportRepository extends JpaRepository<EvacuationReport, Long> {

    Optional<EvacuationReport> findFirstByCourseId(Long courseId);

    @Query("""
        SELECT r FROM EvacuationReport r
        WHERE r.course.id = :courseId
          AND r.course.location.id = :locationId
          AND r.visibleForStudents = true
          AND r.status = nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus.APPROVED
        """)
    List<EvacuationReport> findByCourseIdAndLocationAndApprovedVisible(Long courseId, Long locationId);

    List<EvacuationReport> findByCreatedBy_Id(Long trainerId);
}
