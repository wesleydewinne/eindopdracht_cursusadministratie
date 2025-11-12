package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRegistrationDto {

    @NotNull(message = "courseId mag niet leeg zijn")
    private Long courseId;

    @NotNull(message = "studentId mag niet leeg zijn")
    private Long studentId;
}