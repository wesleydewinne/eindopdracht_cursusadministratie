package nl.novi.eindopdracht_cursusadministratie.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO voor het bijwerken van een bestaande cursus.
 * Wordt gebruikt door de AdminController (PUT /api/admin/courses/{id}).
 */
@Getter
@Setter
public class CourseUpdateRequestDto {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    // mag null zijn bij ontruimingsoefeningen
    private Integer maxParticipants;

    private boolean adminOverrideAllowed;

    // BHV, EHBO, ONTRUIMINGSOEFENING
    private String type;

    // TABLETOP, ANNOUNCED_EVACUATION, UNANNOUNCED_EVACUATION, UNANNOUNCED_WITH_VICTIMS, SMALL_SCENARIO
    private String phase;

    private Long trainerId;
    private Long locationId;

    private boolean reportRequired;
}
