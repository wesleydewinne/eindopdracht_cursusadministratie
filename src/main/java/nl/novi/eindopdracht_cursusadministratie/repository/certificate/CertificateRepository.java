package nl.novi.eindopdracht_cursusadministratie.repository.certificate;

import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    // ------------------------------------------------------------
    // Basisfilters
    // ------------------------------------------------------------
    List<Certificate> findByCourse_Id(Long courseId);

    List<Certificate> findByCourse_Trainer_Id(Long trainerId);

    List<Certificate> findByStudent_Id(Long studentId);

    List<Certificate> findByStudent_EmailIgnoreCase(String email);

    // ------------------------------------------------------------
    // Cursus + student-combinatie (mag ook Optional teruggeven)
    // ------------------------------------------------------------
    Optional<Certificate> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);

    // ------------------------------------------------------------
    // Fetch join: direct cursus + student ophalen (voorkomt N+1)
    // ------------------------------------------------------------
    @Query("""
        SELECT c FROM Certificate c
        JOIN FETCH c.student
        JOIN FETCH c.course
        WHERE c.student.id = :studentId
    """)
    List<Certificate> findByStudent_IdFetchAll(@Param("studentId") Long studentId);
}
