package nl.novi.eindopdracht_cursusadministratie.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponseDto {
    @NotNull private Long id;
    @NotNull private Long courseId;
    @NotNull private String courseTitle;
    @NotNull private Long studentId;
    @NotNull private String studentName;
    @NotNull private LocalDate registrationDate;
    @NotNull private RegistrationStatus status;
}
