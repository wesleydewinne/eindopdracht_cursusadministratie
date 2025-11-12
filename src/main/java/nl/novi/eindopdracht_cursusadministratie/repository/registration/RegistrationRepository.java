package nl.novi.eindopdracht_cursusadministratie.repository.registration;

import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // ------------------------------------------------------------
    // Basisfilters
    // ------------------------------------------------------------
    List<Registration> findByCourse_Id(Long courseId);

    List<Registration> findByCourse_Trainer_Id(Long trainerId);

    List<Registration> findByStudent_Id(Long studentId);

    // ------------------------------------------------------------
    // Cursus + student-combinatie
    // Wordt gebruikt in CertificateServiceTest → generateCertificate()
    // ------------------------------------------------------------
    Optional<Registration> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);

    // ------------------------------------------------------------
    // Controle en tellingen
    // ------------------------------------------------------------
    boolean existsByCourse_IdAndStudent_Id(Long courseId, Long studentId);

    long countByCourse_Id(Long courseId);

    long countByStudent_Id(Long studentId);

    // ------------------------------------------------------------
    // Optioneel: alle goedgekeurde registraties voor specifieke cursus
    // ------------------------------------------------------------
    @Query("""
        SELECT r FROM Registration r
        WHERE r.course.id = :courseId
        AND r.status = :status
    """)
    List<Registration> findByCourseIdAndStatus(
            @Param("courseId") Long courseId,
            @Param("status") RegistrationStatus status
    );
}
