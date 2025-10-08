package nl.novi.eindopdracht_cursusadministratie.controller.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.service.user.CursistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursisten")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CURSIST')")
public class CursistController {

    private final CursistService cursistService;

    //  Eigen gegevens ophalen
    @GetMapping("/{id}")
    public ResponseEntity<Cursist> getCursistById(@PathVariable Long id) {
        return ResponseEntity.ok(cursistService.getCursistById(id));
    }

    //  Certificaten bekijken
    @GetMapping("/{id}/certificaten")
    public ResponseEntity<List<Certificate>> getCertificatesByCursist(@PathVariable Long id) {
        return ResponseEntity.ok(cursistService.getCertificatesByCursist(id));
    }

    //  Inschrijvingen bekijken
    @GetMapping("/{id}/inschrijvingen")
    public ResponseEntity<List<Registration>> getRegistrationsByCursist(@PathVariable Long id) {
        return ResponseEntity.ok(cursistService.getRegistrationsByCursist(id));
    }
}
