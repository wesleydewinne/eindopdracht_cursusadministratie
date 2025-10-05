package nl.novi.eindopdracht_cursusadministratie.repository.certificate;

import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
