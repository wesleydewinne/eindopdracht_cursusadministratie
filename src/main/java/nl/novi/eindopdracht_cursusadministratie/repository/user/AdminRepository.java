package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository voor het beheren van {@link Admin}-entiteiten.
 * <p>
 * Hiermee kunnen admins worden opgehaald, opgeslagen, bijgewerkt en verwijderd.
 * </p>
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
