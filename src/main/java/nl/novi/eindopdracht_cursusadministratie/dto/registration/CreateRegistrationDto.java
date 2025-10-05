package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRegistrationDto {
    private Long courseId;
    private Long studentId;
}
