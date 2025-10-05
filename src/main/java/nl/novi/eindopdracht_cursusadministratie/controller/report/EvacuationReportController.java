package nl.novi.eindopdracht_cursusadministratie.controller.report;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.service.report.EvacuationReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class EvacuationReportController {

    private final EvacuationReportService reportService;

    public EvacuationReportController(EvacuationReportService reportService) {
        this.reportService = reportService;
    }

    //  Alle verslagen ophalen (alleen admin)
    @GetMapping
    public ResponseEntity<List<EvacuationReport>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    //  Alle verslagen van een specifieke cursus
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EvacuationReport>> getReportsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(reportService.getReportsByCourse(courseId));
    }

    //  Alleen goedgekeurde verslagen die zichtbaar zijn voor cursisten
    @GetMapping("/course/{courseId}/visible")
    public ResponseEntity<List<EvacuationReport>> getVisibleReports(@PathVariable Long courseId) {
        return ResponseEntity.ok(reportService.getVisibleReports(courseId));
    }

    //  Specifiek verslag ophalen op ID
    @GetMapping("/{id}")
    public ResponseEntity<EvacuationReport> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    //  Nieuw verslag aanmaken door trainer
    @PostMapping
    public ResponseEntity<EvacuationReport> createReport(
            @RequestParam Long courseId,
            @RequestParam Long trainerId,
            @RequestBody EvacuationReport reportData
    ) {
        EvacuationReport createdReport = reportService.createReport(courseId, trainerId, reportData);
        return ResponseEntity.ok(createdReport);
    }

    //  Verslag bijwerken zolang status = PENDING
    @PutMapping("/{id}")
    public ResponseEntity<EvacuationReport> updateReport(
            @PathVariable Long id,
            @RequestBody EvacuationReport updatedData
    ) {
        EvacuationReport updatedReport = reportService.updateReport(id, updatedData);
        return ResponseEntity.ok(updatedReport);
    }

    //  Verslag goedkeuren door admin
    @PutMapping("/{id}/approve")
    public ResponseEntity<EvacuationReport> approveReport(
            @PathVariable Long id,
            @RequestParam Long adminId
    ) {
        EvacuationReport approved = reportService.approveReport(id, adminId);
        return ResponseEntity.ok(approved);
    }

    //  Verslag afkeuren door admin
    @PutMapping("/{id}/reject")
    public ResponseEntity<EvacuationReport> rejectReport(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String remarks
    ) {
        EvacuationReport rejected = reportService.rejectReport(id, adminId, remarks);
        return ResponseEntity.ok(rejected);
    }

    //  Verslag verwijderen (alleen admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
