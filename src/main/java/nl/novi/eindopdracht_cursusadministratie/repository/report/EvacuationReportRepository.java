package nl.novi.eindopdracht_cursusadministratie.repository.report;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvacuationReportRepository extends JpaRepository<EvacuationReport, Long> {

    List<EvacuationReport> findByCourseId(Long courseId);

    List<EvacuationReport> findByCourseIdAndVisibleForStudentsTrue(Long courseId);
}
