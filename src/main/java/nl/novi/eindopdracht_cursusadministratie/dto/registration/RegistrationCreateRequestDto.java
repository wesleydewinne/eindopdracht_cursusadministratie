package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationCreateRequestDto {
    @NotNull(message = "courseId is verplicht.")
    private Long courseId;

    @NotNull(message = "studentId is verplicht.")
    private Long studentId;
}
