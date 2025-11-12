package nl.novi.eindopdracht_cursusadministratie.controller.report;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.report.CreateEvacuationReportRequestDto;
import nl.novi.eindopdracht_cursusadministratie.dto.report.EvacuationReportResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.report.EvacuationReportSummaryDto;
import nl.novi.eindopdracht_cursusadministratie.dto.response.DeleteResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.UserNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.report.EvacuationReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin
public class EvacuationReportController {

    private final EvacuationReportService reportService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;


    // ============================================================
    // ADMIN OVERZICHT
    // ============================================================

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EvacuationReportSummaryDto>> getAllReportSummaries() {
        List<EvacuationReportSummaryDto> summaries = reportService.getAllReports().stream()
                .map(r -> new EvacuationReportSummaryDto(
                        r.getId(),
                        r.getCourse() != null ? r.getCourse().getStartDate() : null,
                        r.getPhase() != null ? r.getPhase().name() : null,
                        (r.getCourse() != null && r.getCourse().getLocation() != null)
                                ? r.getCourse().getLocation().getCompanyName()
                                : null
                ))
                .toList();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public ResponseEntity<EvacuationReportResponseDto> getReportById(@PathVariable Long id) {
        EvacuationReport report = reportService.getReportById(id);
        return ResponseEntity.ok(toDto(report));
    }

    @GetMapping("/trainer/{trainerId}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<EvacuationReportResponseDto>> getReportsByTrainer(@PathVariable Long trainerId) {
        List<EvacuationReportResponseDto> dtos = reportService.getReportsByTrainer(trainerId)
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }


    // ============================================================
    // TRAINER ACTIES
    // ============================================================

    @PostMapping
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<EvacuationReportResponseDto> createReport(
            @RequestParam Long courseId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateEvacuationReportRequestDto dto
    ) {
        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Trainer niet gevonden."));

        EvacuationReport reportData = new EvacuationReport();
        reportData.setPhase(EvacuationPhase.valueOf(dto.phase()));
        reportData.setEvacuationTimeMinutes(dto.evacuationTimeMinutes());
        reportData.setBuildingSize(dto.buildingSize());
        reportData.setObservations(dto.observations());
        reportData.setImprovements(dto.improvements());

        EvacuationReport created = reportService.createReport(courseId, trainer.getId(), reportData);
        return ResponseEntity.ok(toDto(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<EvacuationReportResponseDto> updateReport(
            @PathVariable Long id,
            @RequestBody EvacuationReport updatedData,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Trainer niet gevonden."));

        EvacuationReport updated = reportService.updateReport(id, updatedData, trainer);
        return ResponseEntity.ok(toDto(updated));
    }


    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EvacuationReportResponseDto> approveReport(
            @PathVariable Long id,
            @RequestParam Long adminId
    ) {
        EvacuationReport approved = reportService.approveReport(id, adminId);
        return ResponseEntity.ok(toDto(approved));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EvacuationReportResponseDto> rejectReport(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam String remarks
    ) {
        EvacuationReport rejected = reportService.rejectReport(id, adminId, remarks);
        return ResponseEntity.ok(toDto(rejected));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteReport(@PathVariable Long id) {
        boolean deleted = reportService.deleteReportById(id);

        if (!deleted) {
            return ResponseEntity.status(404)
                    .body(new DeleteResponseDto("❌ Ontruimingsverslag met ID " + id + " is niet gevonden of kon niet worden verwijderd."));
        }

        return ResponseEntity.ok(new DeleteResponseDto("✅ Ontruimingsverslag met ID " + id + " is succesvol verwijderd."));
    }


    // ============================================================
    // PDF DOWNLOAD
    // ============================================================

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','CURSIST')")
    public ResponseEntity<byte[]> downloadReportPdf(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return getReportPdfResponse(id, userDetails, true);
    }

    @GetMapping("/{id}/pdf/inline")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','CURSIST')")
    public ResponseEntity<byte[]> viewReportPdfInline(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return getReportPdfResponse(id, userDetails, false);
    }


    // ============================================================
    // GEMEENSCHAPPELIJKE LOGICA VOOR PDF
    // ============================================================

    private ResponseEntity<byte[]> getReportPdfResponse(Long id, UserDetails userDetails, boolean asDownload) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden."));
        EvacuationReport report = reportService.getReportById(id);

        boolean isTrainer = user.getRole().name().equalsIgnoreCase("TRAINER");
        boolean isCursist = user.getRole().name().equalsIgnoreCase("CURSIST");

        if (isTrainer && !report.getCreatedBy().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Je kunt alleen je eigen verslagen downloaden.")
                    .build();
        }
        if (isTrainer && report.getStatus() != ReportStatus.APPROVED) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Verslag is nog niet goedgekeurd.")
                    .build();
        }
        if (isCursist) {
            if (report.getStatus() != ReportStatus.APPROVED || !report.isVisibleForStudents()) {
                return ResponseEntity.status(403)
                        .header(HttpHeaders.WARNING, "Verslag is niet zichtbaar of nog niet goedgekeurd.")
                        .build();
            }
            Course course = report.getCourse();
            if (course == null || course.getLocation() == null) {
                return ResponseEntity.status(403)
                        .header(HttpHeaders.WARNING, "Geen locatie-informatie gevonden voor dit verslag.")
                        .build();
            }
            boolean volgtOpZelfdeLocatie = courseRepository.existsByLocation_IdAndRegistrations_Student_Id(
                    course.getLocation().getId(),
                    user.getId()
            );
            if (!volgtOpZelfdeLocatie) {
                return ResponseEntity.status(403)
                        .header(HttpHeaders.WARNING, "Je bent niet ingeschreven voor een training op deze locatie.")
                        .build();
            }
        }

        byte[] pdf = reportService.getReportPdf(id);
        if (pdf == null || pdf.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String disposition = asDownload ? "attachment" : "inline";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; filename=evacuation_report_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // ============================================================
    // DTO HELPER
    // ============================================================

    private EvacuationReportResponseDto toDto(EvacuationReport r) {
        return new EvacuationReportResponseDto(
                r.getId(),
                r.getCourse() != null ? r.getCourse().getName() : null,
                r.getPhase() != null ? r.getPhase().name() : null,
                r.getEvacuationTimeMinutes(),
                r.getBuildingSize(),
                r.getObservations(),
                r.getImprovements(),
                r.getEvaluationAdvice(),
                r.getStatus()
        );
    }
}
