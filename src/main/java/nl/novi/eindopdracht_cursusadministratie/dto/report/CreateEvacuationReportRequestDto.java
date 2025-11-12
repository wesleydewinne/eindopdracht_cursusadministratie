package nl.novi.eindopdracht_cursusadministratie.dto.report;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateEvacuationReportRequestDto(
        @NotNull String phase,
        @Positive int evacuationTimeMinutes,
        @NotNull String buildingSize,
        String observations,
        String improvements
) {}
