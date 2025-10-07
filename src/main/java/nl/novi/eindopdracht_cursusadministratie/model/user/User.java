package nl.novi.eindopdracht_cursusadministratie.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * De {@code User}-klasse is de superklasse voor alle gebruikers in het systeem.
 * <p>
 * Deze klasse bevat de gedeelde eigenschappen voor alle typen gebruikers:
 * {@code id}, {@code name}, {@code email}, {@code password} en {@code role}.
 * Subklassen (zoals {@code Admin}, {@code Trainer} en {@code Student})
 * erven deze velden en kunnen eigen specifieke attributen toevoegen.
 * </p>
 *
 * <p>
 * De klasse maakt gebruik van {@link InheritanceType#JOINED}, wat betekent dat
 * elke subklasse een eigen tabel krijgt, maar de gemeenschappelijke kolommen
 * worden opgeslagen in de hoofdtabel {@code users}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public abstract class User {

    /** Unieke ID van de gebruiker (automatisch gegenereerd). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Volledige naam van de gebruiker. */
    @Column(nullable = false)
    private String name;

    /** E-mailadres van de gebruiker (moet uniek zijn). */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Wachtwoord van de gebruiker.
     * <p>
     * Dit veld is gemarkeerd met {@link JsonIgnore} om te voorkomen dat
     * het zichtbaar is in API-responses of logs.
     * </p>
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /**
     * De rol van de gebruiker.
     * <p>
     * Dit wordt later gebruikt voor toegangsbeheer (security).
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
