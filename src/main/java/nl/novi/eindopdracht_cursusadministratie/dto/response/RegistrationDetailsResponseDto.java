package nl.novi.eindopdracht_cursusadministratie.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

import java.time.LocalDate;

/**
 * DTO voor het teruggeven van inschrijvingsinformatie.
 * Wordt o.a. gebruikt bij het ophalen of bijwerken van inschrijvingen.
 */
@Getter
@Setter
@AllArgsConstructor
public class RegistrationDetailsResponseDto {

    @NotNull private Long registrationId;
    @NotNull private Long courseId;
    @NotNull private String courseTitle;
    @NotNull private Long studentId;
    @NotNull private String studentName;
    @NotNull private LocalDate registrationDate;
    @NotNull private RegistrationStatus status;
}
