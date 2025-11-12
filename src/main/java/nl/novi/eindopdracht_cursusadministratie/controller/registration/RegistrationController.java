package nl.novi.eindopdracht_cursusadministratie.controller.registration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.*;
import nl.novi.eindopdracht_cursusadministratie.dto.response.DeleteResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.UnauthorizedAccessException;
import nl.novi.eindopdracht_cursusadministratie.helper.RegistrationMapperHelper;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.registration.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor inschrijvingen (registraties).
 * Rollen:
 * - ADMIN → kan alles bekijken, toevoegen en verwijderen
 * - TRAINER → kan alleen eigen cursussen beheren
 */
@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;


    // ============================================================
    // ADMIN OVERZICHT
    // ============================================================

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegistrationTrainerResponseDto>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(
                registrations.stream()
                        .map(RegistrationMapperHelper::toTrainerDto)
                        .toList()
        );
    }


    // ============================================================
    // TRAINER OVERZICHT
    // ============================================================

    @GetMapping("/trainer/{trainerId}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<RegistrationTrainerResponseDto>> getRegistrationsByTrainer(@PathVariable Long trainerId) {
        List<Registration> registrations = registrationService.getRegistrationsByTrainer(trainerId);
        return ResponseEntity.ok(
                registrations.stream()
                        .map(RegistrationMapperHelper::toTrainerDto)
                        .toList()
        );
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN')")
    public ResponseEntity<List<RegistrationTrainerResponseDto>> getRegistrationsByCourse(@PathVariable Long courseId) {
        List<Registration> registrations = registrationService.getRegistrationsByCourse(courseId);
        return ResponseEntity.ok(
                registrations.stream()
                        .map(RegistrationMapperHelper::toTrainerDto)
                        .toList()
        );
    }


    // ============================================================
    // TRAINER ACTIES
    // ============================================================

    @PutMapping("/{registrationId}/update")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<RegistrationStatusResponseDto> updateAttendanceAndStatus(
            @PathVariable Long registrationId,
            @RequestParam boolean attendance,
            @RequestParam(required = false) RegistrationStatus status,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UnauthorizedAccessException("Trainer niet gevonden of niet ingelogd."));

        RegistrationStatusResponseDto dto = registrationService.updateAttendanceAndStatus(
                registrationId, attendance, status, trainer
        );
        return ResponseEntity.ok(dto);
    }


    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    /**
     * Admin kan een cursist aan een cursus toevoegen (nieuwe inschrijving maken).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegistrationResponseDto> createRegistration(@Valid @RequestBody RegistrationCreateRequestDto dto) {
        Registration registration = registrationService.createRegistration(dto);

        RegistrationResponseDto response = new RegistrationResponseDto(
                registration.getId(),
                registration.getCourse().getId(),
                registration.getCourse().getName(),
                registration.getStudent().getId(),
                registration.getStudent().getName(),
                registration.getRegistrationDate(),
                registration.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Admin kan de status van een registratie wijzigen (bijv. CANCELLED of COMPLETED).
     */
    @PutMapping("/{registrationId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegistrationStatusResponseDto> updateStatusAsAdmin(
            @PathVariable Long registrationId,
            @RequestParam RegistrationStatus newStatus,
            @RequestParam Long adminId
    ) {
        RegistrationStatusResponseDto dto =
                registrationService.updateStatusAsAdmin(registrationId, newStatus, adminId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Admin kan een inschrijving verwijderen (alleen de koppeling tussen cursus en cursist).
     */
    @DeleteMapping("/{registrationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteRegistration(@PathVariable Long registrationId) {
        Registration deleted = registrationService.deleteRegistrationAndReturn(registrationId);

        String message = "Inschrijving met ID " + deleted.getId() +
                " (cursus: " + deleted.getCourse().getName() +
                ", cursist: " + deleted.getStudent().getName() + ") is succesvol verwijderd.";

        return ResponseEntity.ok(new DeleteResponseDto(message));
    }
}
