package nl.novi.eindopdracht_cursusadministratie.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * De {@code Admin}-klasse vertegenwoordigt een systeembeheerder.
 * <p>
 * Een admin heeft toegang tot alle functionaliteiten van de cursusadministratie,
 * waaronder het beheren van gebruikers, cursussen, certificaten en rapporten.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {

    /**
     * Afdeling of organisatieonderdeel van de admin (optioneel).
     * Kan gebruikt worden voor toekomstige filtering of beheerstructuur.
     */
    private String department;

    /** Standaardconstructor die de rol automatisch instelt op ADMIN. */
    public Admin(String name, String email, String password, String department) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.ADMIN);
        this.department = department;
    }
}
