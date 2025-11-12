package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

/**
 * DTO voor trainers om de inschrijvingen van hun cursisten te bekijken.
 * Bevat beperkte, relevante informatie (geen volledige entiteiten).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationTrainerResponseDto {

    private Long id;
    private Long studentId;
    private String studentName;
    private boolean attendance;
    private RegistrationStatus status;
}
