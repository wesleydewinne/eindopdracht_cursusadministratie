package nl.novi.eindopdracht_cursusadministratie.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.GenerateCertificateRequest;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseCompletionSummaryDto;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationStatusResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.TrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.TrainerUpdateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.helper.TrainerCourseOwnershipHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.TrainerSecurityHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.service.user.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin
public class TrainerController {

    private final TrainerService trainerService;


    // ================================================================
    // TRAINER PROFIEL
    // ================================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<TrainerResponseDto> getTrainerById(@PathVariable Long id) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<TrainerResponseDto> updateTrainer(
            @PathVariable Long id,
            @RequestBody TrainerUpdateRequestDto dto
    ) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();
        return ResponseEntity.ok(trainerService.updateTrainer(id, dto));
    }


    // ================================================================
    // CURSUSSEN
    // ================================================================

    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseTrainerResponseDto>> getMyCourses() {
        Long trainerId = TrainerSecurityHelper.getAuthenticatedTrainerId();
        return ResponseEntity.ok(trainerService.getCoursesByTrainer(trainerId));
    }

    @PutMapping("/courses/{courseId}/complete")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<CourseCompletionSummaryDto> completeCourse(@PathVariable Long courseId) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();
        Course course = trainerService.getCourseEntity(courseId);
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        return ResponseEntity.ok(trainerService.completeCourse(courseId));
    }


    // ================================================================
    // INSCHRIJVINGEN / AANWEZIGHEID / STATUS
    // ================================================================

    @PutMapping("/courses/{courseId}/registrations/{registrationId}/attendance")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<RegistrationStatusResponseDto> markAttendanceForCourse(
            @PathVariable Long courseId,
            @PathVariable Long registrationId,
            @RequestParam boolean aanwezig
    ) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = trainerService.getCourseEntity(courseId);
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        return ResponseEntity.ok(trainerService.markAttendanceForCourse(courseId, registrationId, aanwezig));
    }

    @PutMapping("/courses/{courseId}/registrations/{registrationId}/status")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<RegistrationStatusResponseDto> updateRegistrationStatusForCourse(
            @PathVariable Long courseId,
            @PathVariable Long registrationId,
            @RequestParam RegistrationStatus status
    ) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = trainerService.getCourseEntity(courseId);
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        return ResponseEntity.ok(trainerService.updateRegistrationStatusForCourse(courseId, registrationId, status));
    }

    /**
     * Geeft alle aanwezige cursisten die nog niet beoordeeld zijn.
     */
    @GetMapping("/courses/{courseId}/registrations/unreviewed")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<List<RegistrationStatusResponseDto>> getUnreviewedRegistrationsForCourse(
            @PathVariable Long courseId
    ) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = trainerService.getCourseEntity(courseId);
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        return ResponseEntity.ok(trainerService.getUnreviewedRegistrationsForCourse(courseId));
    }


    // ================================================================
    // CERTIFICATEN
    // ================================================================

    @PostMapping("/certificates")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<Certificate> generateCertificate(@Valid @RequestBody GenerateCertificateRequest req) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();

        Course course = trainerService.getCourseEntity(req.courseId());
        TrainerCourseOwnershipHelper.verifyOwnershipOrAdmin(course);

        Certificate createdCertificate = trainerService.generateCertificate(
                req.courseId(),
                req.studentId(),
                req.issuedBy()
        );
        return ResponseEntity.ok(createdCertificate);
    }

    @GetMapping("/{trainerId}/certificates")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<List<Certificate>> getCertificatesByTrainer(@PathVariable Long trainerId) {
        TrainerSecurityHelper.verifyTrainerOrAdmin();
        return ResponseEntity.ok(trainerService.getCertificatesByTrainer(trainerId));
    }
}
