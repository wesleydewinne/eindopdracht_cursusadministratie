package nl.novi.eindopdracht_cursusadministratie.dto.course;

import java.util.List;

public record CourseCompletionSummaryDto(
        Long courseId,
        String courseName,
        String trainerName,
        List<String> approved,
        List<String> cancelled,
        List<String> absent
) {}
