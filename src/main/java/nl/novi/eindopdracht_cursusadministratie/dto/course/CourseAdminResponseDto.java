package nl.novi.eindopdracht_cursusadministratie.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationGroupStudentDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CourseAdminResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxParticipants;
    private boolean adminOverrideAllowed;
    private String type;

    private String address;
    private String postalCode;
    private String city;

    private String trainerName;

    private Boolean reportRequired;
    private Boolean evacuationTraining;

    private List<RegistrationGroupStudentDto> students;
}
