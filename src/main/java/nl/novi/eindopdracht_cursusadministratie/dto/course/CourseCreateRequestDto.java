package nl.novi.eindopdracht_cursusadministratie.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO voor het aanmaken van een nieuwe cursus.
 * Wordt gebruikt door AdminController → AdminService.
 */
@Getter
@Setter
public class CourseCreateRequestDto {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipants;
    private boolean adminOverrideAllowed;
    private String type;
    private Long locationId;
    private Long trainerId;
    private boolean reportRequired;
    private String phase;
}
