package nl.novi.eindopdracht_cursusadministratie.repository.registration;

import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Alle inschrijvingen van een cursist
    List<Registration> findByStudent_Id(Long studentId);

    // Alle inschrijvingen voor een specifieke cursus
    @SuppressWarnings("unused")
    List<Registration> findByCourse_Id(Long courseId);

    // Optioneel: inschrijving van een specifieke cursist voor een specifieke cursus
    @SuppressWarnings("unused")
    List<Registration> findByCourse_IdAndStudent_Id(Long courseId, Long studentId);
}
