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

/**
 * Serviceklasse voor cursisten.
 *
 * Taken:
 * - Ophalen van cursistgegevens
 * - Ophalen van certificaten van de cursist
 * - Ophalen van inschrijvingen (registraties) van de cursist
 */
@Service
@RequiredArgsConstructor
public class CursistService {

    private final CursistRepository cursistRepository;
    private final CertificateRepository certificateRepository;
    private final RegistrationRepository registrationRepository;

    // ============================================================
    // CURSIST GEGEVENS
    // ============================================================

    /**
     * Haalt één specifieke cursist op.
     * Gooit een CursistNotFoundException als deze niet bestaat.
     */
    public Cursist getCursistById(Long cursistId) {
        return findEntityById(cursistId, cursistRepository, new CursistNotFoundException(cursistId));
    }

    // ============================================================
    // CERTIFICATEN
    // ============================================================

    /**
     * Haalt alle certificaten op die gekoppeld zijn aan de cursist.
     */
    public List<Certificate> getCertificatesByCursist(Long cursistId) {

        findEntityById(cursistId, cursistRepository, new CursistNotFoundException(cursistId));
        return certificateRepository.findByStudent_Id(cursistId);
    }

    // ============================================================
    // INSCHRIJVINGEN
    // ============================================================

    /**
     * Haalt alle inschrijvingen (registraties) van een specifieke cursist op.
     */
    public List<Registration> getRegistrationsByCursist(Long cursistId) {

        findEntityById(cursistId, cursistRepository, new CursistNotFoundException(cursistId));

        return registrationRepository.findByStudent_Id(cursistId);
    }
}
