package nl.novi.eindopdracht_cursusadministratie.service;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.CursistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursistService {

    private final CursistRepository cursistRepository;
    private final RegistrationRepository registrationRepository;
    private final CertificateRepository certificateRepository;

    //  Eigen gegevens ophalen
    public Cursist getCursistById(Long id) {
        return cursistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cursist not found with id: " + id));
    }

    //  Certificaten van deze cursist ophalen
    public List<Certificate> getCertificatesByCursist(Long cursistId) {
        return certificateRepository.findByCursistId(cursistId);
    }

    //  Inschrijvingen van deze cursist ophalen
    public List<Registration> getRegistrationsByCursist(Long cursistId) {
        return registrationRepository.findByCursistId(cursistId);
    }

}
