package nl.novi.eindopdracht_cursusadministratie.dto.course;

import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationTrainerCompactDto;
import java.time.LocalDate;

public record TrainerCourseSummaryDto(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        int numberOfRegistrations,
        LocationTrainerCompactDto location
) {}
