package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

/**
 * DTO die een samenvatting teruggeeft van de inschrijving
 * nadat de trainer aanwezigheid of status heeft gewijzigd.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationStatusResponseDto {

    private Long registrationId;
    private Long studentId;
    private String studentName;
    private boolean present;
    private RegistrationStatus status;
    private String courseName;
}
