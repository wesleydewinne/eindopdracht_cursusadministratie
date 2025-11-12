package nl.novi.eindopdracht_cursusadministratie.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO voor loginverzoeken (email + wachtwoord).
 */
@Getter
@Setter
public class AuthenticationRequest {

    @NotBlank(message = "E-mailadres is verplicht.")
    @Email(message = "Voer een geldig e-mailadres in.")
    private String email;

    @NotBlank(message = "Wachtwoord is verplicht.")
    private String password;
}
