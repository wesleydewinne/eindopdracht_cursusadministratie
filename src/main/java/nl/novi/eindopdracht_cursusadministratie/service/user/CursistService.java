package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.CursistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
@RequiredArgsConstructor
public class CursistService {

    private final CursistRepository cursistRepository;
    private final RegistrationRepository registrationRepository;
    private final CertificateRepository certificateRepository;

    // ============================================================
    //  CURSIST GEGEVENS
    // ============================================================

    /**
     * Haalt de gegevens van een specifieke cursist op.
     *
     * @param id ID van de cursist
     * @return De gevonden cursist
     */
    public Cursist getCursistById(Long id) {
        return findEntityById(id, cursistRepository, new CursistNotFoundException(id));
    }

    // ============================================================
    //  CERTIFICATEN
    // ============================================================

    /**
     * Haalt alle certificaten van een specifieke cursist op.
     *
     * @param cursistId ID van de cursist
     * @return Lijst van certificaten
     */
    public List<Certificate> getCertificatesByCursist(Long cursistId) {
        // Controleer eerst of de cursist bestaat
        findEntityById(cursistId, cursistRepository, new CursistNotFoundException(cursistId));
        return certificateRepository.findByStudent_Id(cursistId);
    }

    // ============================================================
    //  INSCHRIJVINGEN
    // ============================================================

    /**
     * Haalt alle inschrijvingen van een specifieke cursist op.
     *
     * @param cursistId ID van de cursist
     * @return Lijst van inschrijvingen
     */
    public List<Registration> getRegistrationsByCursist(Long cursistId) {
        // Controleer eerst of de cursist bestaat
        findEntityById(cursistId, cursistRepository, new CursistNotFoundException(cursistId));
        return registrationRepository.findByStudent_Id(cursistId);
    }
}
