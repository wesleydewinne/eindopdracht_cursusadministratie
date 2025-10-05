package nl.novi.eindopdracht_cursusadministratie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponseDto {
    private Long id;
    private Long courseId;
    private String courseTitle;
    private Long studentId;
    private String studentName;
    private LocalDate registrationDate;
    private RegistrationStatus status;
}
