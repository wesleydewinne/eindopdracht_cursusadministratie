package nl.novi.eindopdracht_cursusadministratie.dto.report;

import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;

public record EvacuationReportResponseDto(
        Long id,
        String courseName,
        String phase,
        int evacuationTimeMinutes,
        String buildingSize,
        String observations,
        String improvements,
        String evaluationAdvice,
        ReportStatus status
) {}
