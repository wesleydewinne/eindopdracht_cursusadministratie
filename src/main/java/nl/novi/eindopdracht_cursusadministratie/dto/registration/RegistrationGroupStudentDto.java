package nl.novi.eindopdracht_cursusadministratie.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationGroupStudentDto {
    private Long studentId;
    private String studentName;
}
