package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursistRepository extends JpaRepository<Cursist, Long> {
}
