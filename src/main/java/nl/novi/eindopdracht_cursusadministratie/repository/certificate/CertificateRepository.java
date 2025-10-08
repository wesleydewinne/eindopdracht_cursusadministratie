package nl.novi.eindopdracht_cursusadministratie.repository.certificate;

import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    // Alle certificaten van één cursus
    @SuppressWarnings("unused")
    List<Certificate> findByCourse_Id(Long courseId);

    // Alle certificaten uitgegeven in cursussen van een specifieke trainer
    List<Certificate> findByCourse_Trainer_Id(Long trainerId);

    // Alle certificaten behaald door een specifieke cursist
    List<Certificate> findByStudent_Id(Long studentId);

    // Certificaat van één specifieke cursist binnen een specifieke cursus
    @SuppressWarnings("unused")
    List<Certificate> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);
}
