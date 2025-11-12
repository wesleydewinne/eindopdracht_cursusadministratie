package nl.novi.eindopdracht_cursusadministratie.dto.report;

import java.time.LocalDate;

/**
 * Compact overzicht van ontruimingsverslagen (alleen id, datum, fase en bedrijfsnaam).
 */
public record EvacuationReportSummaryDto(
        Long id,
        LocalDate startDate,
        String phase,
        String companyName
) {}
