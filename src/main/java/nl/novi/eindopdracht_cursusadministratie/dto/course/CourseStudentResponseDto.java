package nl.novi.eindopdracht_cursusadministratie.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationStudentResponseDto;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CourseStudentResponseDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocationStudentResponseDto location;
}
