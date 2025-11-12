package nl.novi.eindopdracht_cursusadministratie.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Simpele en veilige weergave van een cursist.
 * Bevat alleen basisgegevens, geen wachtwoord of gekoppelde entiteiten.
 */
@Getter
@Setter
@AllArgsConstructor
public class CursistResponseDto {
    private Long id;
    private String name;
    private String email;
    private boolean active;
}
