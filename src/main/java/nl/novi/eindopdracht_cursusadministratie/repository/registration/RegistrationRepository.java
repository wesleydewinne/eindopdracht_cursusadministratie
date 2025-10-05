package nl.novi.eindopdracht_cursusadministratie.repository.registration;

import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
