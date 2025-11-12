package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

import java.time.LocalDate;

/**
 * DTO voor het teruggeven van registratie-informatie.
 * Wordt gebruikt door Admin en Trainer bij het aanmaken of ophalen van registraties.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private Long courseId;

    @NotNull
    private String courseTitle;

    @NotNull
    private Long studentId;

    @NotNull
    private String studentName;

    @NotNull
    private LocalDate registrationDate;

    @NotNull
    private RegistrationStatus status;
}
