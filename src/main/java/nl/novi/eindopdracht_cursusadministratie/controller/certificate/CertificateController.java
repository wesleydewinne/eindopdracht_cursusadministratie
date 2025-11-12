package nl.novi.eindopdracht_cursusadministratie.controller.certificate;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.GenerateCertificateRequest;
import nl.novi.eindopdracht_cursusadministratie.dto.response.DeleteResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.UserNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.certificate.CertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor certificaten.
 *
 * Rollen:
 *  - ADMIN → kan alles bekijken, verlopen certificaten ophalen en verwijderen
 *  - TRAINER → kan certificaten genereren voor eigen cursisten
 *  - CURSIST → kan alleen zijn eigen certificaten bekijken of downloaden
 */
@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@CrossOrigin
public class CertificateController {

    private final CertificateService certificateService;
    private final UserRepository userRepository;


    // ============================================================
    // ADMIN & TRAINER OVERZICHT
    // ============================================================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public ResponseEntity<List<CertificateResponseDto>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

    @GetMapping("/trainer/{trainerId}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<CertificateResponseDto>> getCertificatesByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(certificateService.getCertificatesByTrainer(trainerId));
    }


    // ============================================================
    // CERTIFICAAT GENEREREN (TRAINER)
    // ============================================================

    @PostMapping("/generate")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<CertificateResponseDto> generateCertificate(
            @RequestBody GenerateCertificateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Trainer niet gevonden."));

        CertificateResponseDto created = certificateService.generateCertificate(
                request.courseId(),
                request.studentId(),
                trainer.getId()
        );

        return ResponseEntity.ok(created);
    }


    // ============================================================
    // CERTIFICAAT OPHALEN / BEKIJKEN
    // ============================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','CURSIST')")
    public ResponseEntity<CertificateResponseDto> getCertificateById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        CertificateResponseDto certDto = certificateService.getCertificateById(id);
        Certificate cert = certificateService.getCertificateEntity(id); // extra voor securitycontrole
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden."));

        boolean isTrainer = user.getRole().name().equalsIgnoreCase("TRAINER");
        boolean isCursist = user.getRole().name().equalsIgnoreCase("CURSIST");

        if (isCursist && !cert.getStudent().getEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Je kunt geen certificaten van andere cursisten bekijken.")
                    .build();
        }

        if (isTrainer && !cert.getCourse().getTrainer().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Je kunt alleen certificaten van je eigen cursussen bekijken.")
                    .build();
        }

        return ResponseEntity.ok(certDto);
    }


    // ============================================================
    // PDF DOWNLOAD (ALLE ROLLEN)
    // ============================================================

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','CURSIST')")
    public ResponseEntity<byte[]> downloadCertificatePdf(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Certificate cert = certificateService.getCertificateEntity(id);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Gebruiker niet gevonden."));

        boolean isTrainer = user.getRole().name().equalsIgnoreCase("TRAINER");
        boolean isCursist = user.getRole().name().equalsIgnoreCase("CURSIST");

        if (isCursist && !cert.getStudent().getEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Je kunt alleen je eigen certificaten downloaden.")
                    .build();
        }

        if (isTrainer && !cert.getCourse().getTrainer().getId().equals(user.getId())) {
            return ResponseEntity.status(403)
                    .header(HttpHeaders.WARNING, "Je kunt alleen certificaten van je eigen cursussen downloaden.")
                    .build();
        }

        byte[] pdf = certificateService.getCertificatePdf(id);
        if (pdf == null || pdf.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String filename = "certificate_" + cert.getCertificateNumber() + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    // ============================================================
    // ADMIN ACTIES
    // ============================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        String message = "🪪 Certificaat met ID " + id + " is succesvol verwijderd.";
        return ResponseEntity.ok(new DeleteResponseDto(message));
    }

    @GetMapping("/expired")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CertificateResponseDto>> getExpiredCertificates() {
        return ResponseEntity.ok(certificateService.getExpiredCertificates());
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CertificateResponseDto>> getCertificatesExpiringSoon(@RequestParam int days) {
        return ResponseEntity.ok(certificateService.getCertificatesExpiringSoon(days));
    }


    // ============================================================
    // CURSIST EIGEN CERTIFICATEN
    // ============================================================

    @GetMapping("/mine")
    @PreAuthorize("hasRole('CURSIST')")
    public ResponseEntity<List<CertificateResponseDto>> getMyCertificates(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Cursist niet gevonden."));

        List<CertificateResponseDto> myDtos = certificateService.getCertificatesByCursistEmail(user.getEmail());

        if (myDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(myDtos);
    }
}
