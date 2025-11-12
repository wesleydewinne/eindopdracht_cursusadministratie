package nl.novi.eindopdracht_cursusadministratie.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO die wordt teruggegeven na succesvolle login.
 * Bevat de JWT-token en kerngegevens van de gebruiker.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String jwt;
    private Long id;
    private String email;
    private String role;
    private String name;
    private long expiresAt; // Epoch timestamp van vervaldatum
}
