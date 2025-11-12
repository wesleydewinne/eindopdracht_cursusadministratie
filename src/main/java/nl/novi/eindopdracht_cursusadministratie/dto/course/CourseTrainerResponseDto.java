package nl.novi.eindopdracht_cursusadministratie.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationTrainerResponseDto;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CourseTrainerResponseDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfRegistrations;
    private LocationTrainerResponseDto location;

    private List<RegistrationTrainerResponseDto> registrations;
}
