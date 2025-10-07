package nl.novi.eindopdracht_cursusadministratie.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * De {@code User}-entiteit vertegenwoordigt een systeemgebruiker:
 * dit kan een Administrator, Trainer of Cursist zijn.
 * <p>
 * Elke gebruiker heeft:
 * <ul>
 *   <li>Een uniek database-ID</li>
 *   <li>Een rol-specifiek ID (via {@link nl.novi.eindopdracht_cursusadministratie.helper.RoleIdHelper})</li>
 *   <li>Een naam en e-mailadres</li>
 *   <li>Een specifieke {@link Role}</li>
 * </ul>
 * <p>
 * Dit model gebruikt Lombok-annotaties om boilerplate-code zoals getters,
 * setters en een no-args-constructor te genereren.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /** Primair sleutelveld dat automatisch door de database wordt gegenereerd. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Naam van de gebruiker. */
    private String name;

    /** E-mailadres van de gebruiker. */
    private String email;

    /**
     * Uniek ID binnen de rol van de gebruiker (bijv. Trainer 1, Trainer 2, Cursist 1, ...).
     * Dit wordt automatisch gegenereerd via {@code RoleIdHelper}.
     */
    private Integer roleSpecificId;

    /**
     * De rol van de gebruiker — bepaalt toegangsrechten binnen de applicatie.
     */
    @Enumerated(EnumType.STRING)
    private Role role;
}
