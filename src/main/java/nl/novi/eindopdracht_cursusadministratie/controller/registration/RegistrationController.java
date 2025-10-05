package nl.novi.eindopdracht_cursusadministratie.controller.registration;

import nl.novi.eindopdracht_cursusadministratie.dto.registration.CreateRegistrationDto;
import nl.novi.eindopdracht_cursusadministratie.dto.response.RegistrationResponseDto;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import nl.novi.eindopdracht_cursusadministratie.service.registration.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // ✅ Alle inschrijvingen ophalen
    @GetMapping
    public List<RegistrationResponseDto> getAllRegistrations() {
        return registrationService.getAllRegistrationDtos();
    }

    // ✅ Inschrijving ophalen op ID
    @GetMapping("/{id}")
    public RegistrationResponseDto getRegistrationById(@PathVariable Long id) {
        return registrationService.getRegistrationDtoById(id);
    }

    // ✅ Nieuwe inschrijving aanmaken (met JSON-body)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public RegistrationResponseDto createRegistration(@RequestBody CreateRegistrationDto dto) {
        return registrationService.createRegistrationDto(dto.getCourseId(), dto.getStudentId());
    }

    // ✅ Status van inschrijving wijzigen
    @PutMapping("/{id}/status")
    public ResponseEntity<RegistrationResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestParam RegistrationStatus status) {
        return ResponseEntity.ok(registrationService.updateRegistrationStatusDto(id, status));
    }

    // ✅ Inschrijving verwijderen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}
