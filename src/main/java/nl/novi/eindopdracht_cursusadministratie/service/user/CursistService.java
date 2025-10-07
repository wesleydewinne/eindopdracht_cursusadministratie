package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
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

    // Eigen gegevens ophalen
    public User getCursistById(Long id) {
        return cursistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cursist not found with id: " + id));
    }

    // Certificaten van deze cursist ophalen
    public List<Certificate> getCertificatesByCursist(Long cursistId) {
        return certificateRepository.findByStudent_Id(cursistId);
    }

    // Inschrijvingen van deze cursist ophalen
    public List<Registration> getRegistrationsByCursist(Long cursistId) {
        return registrationRepository.findByStudent_Id(cursistId);
    }
}
