package nl.novi.eindopdracht_cursusadministratie.controller.certificate;

import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.service.certificate.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    //  Alle certificaten ophalen
    @GetMapping
    public List<Certificate> getAllCertificates() {
        return certificateService.getAllCertificates();
    }

    //  Certificaat ophalen op ID
    @GetMapping("/{id}")
    public Certificate getCertificateById(@PathVariable Long id) {
        return certificateService.getCertificateById(id);
    }

    //  Certificaat aanmaken (student + course ID)
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        return ResponseEntity.ok(certificateService.createCertificate(studentId, courseId));
    }

    //  Certificaat verwijderen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
