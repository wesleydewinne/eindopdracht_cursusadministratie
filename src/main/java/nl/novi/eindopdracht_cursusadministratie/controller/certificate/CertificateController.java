package nl.novi.eindopdracht_cursusadministratie.controller.certificate;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.service.certificate.CertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor het beheren en downloaden van certificaten.
 * Beheert endpoints voor aanmaken, ophalen, verwijderen en downloaden van certificaten.
 */
@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * Haalt alle certificaten op.
     */
    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

    /**
     * Haalt één certificaat op op basis van ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificateById(@PathVariable Long id) {
        return ResponseEntity.ok(certificateService.getCertificateById(id));
    }

    /**
     * Maakt een nieuw certificaat aan voor een cursist en cursus.
     *
     * @param studentId ID van de cursist
     * @param courseId  ID van de cursus
     */
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        Certificate created = certificateService.createCertificate(studentId, courseId);
        return ResponseEntity.ok(created);
    }

    /**
     * Downloadt een certificaat in PDF-formaat (rechtstreeks uit de database).
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id) {
        Certificate cert = certificateService.getCertificateById(id);

        if (cert.getPdfData() == null || cert.getPdfData().length == 0) {
            throw new RuntimeException("Geen PDF-data gevonden voor certificaat ID: " + id);
        }

        String filename = "certificaat_" + cert.getCertificateNumber() + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(cert.getPdfData());
    }

    /**
     * Verwijdert een certificaat op ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
