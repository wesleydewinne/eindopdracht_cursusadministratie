package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository voor het beheren van User-entiteiten.
 *
 * Deze interface biedt methoden om gebruikers op te halen
 * op basis van e-mailadres of rol. Wordt gebruikt door
 * Spring Security (via CustomUserDetailsService) en door
 * de Admin-functionaliteiten.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Zoekt één gebruiker op basis van e-mailadres.
     * Wordt gebruikt voor authenticatie (JWT-login).
     *
     * @param email unieke e-mailadres van de gebruiker
     * @return de gebruiker indien gevonden, anders een lege Optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Haalt alle gebruikers op met een specifieke rol.
     * Handig voor beheerfuncties, bijvoorbeeld alle trainers of cursisten.
     *
     * @param role de rol (ADMIN, TRAINER, CURSIST)
     * @return lijst van gebruikers met de opgegeven rol
     */
    List<User> findByRole(Role role);
}
