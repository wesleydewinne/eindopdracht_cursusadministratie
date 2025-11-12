package nl.novi.eindopdracht_cursusadministratie.model.course;

import lombok.Getter;

/**
 * Enum die de verschillende soorten trainingen beschrijft.
 *
 * - BHV: 1 jaar geldig, geen verslag
 * - EHBO: 2 jaar geldig, geen verslag
 * - ONTRUIMINGSOEFENING: geen certificaat, maar wél een ontruimingsverslag vereist
 *
 * De waarden in deze enum worden gebruikt om automatisch:
 *  - de geldigheid van certificaten te bepalen;
 *  - te bepalen of een verslag verplicht is;
 *  - logica in de CourseService en ReportService te sturen.
 */
@Getter
public enum TrainingType {

    /** Basisopleiding Bedrijfshulpverlening (1 jaar geldig, geen verslag) */
    BHV(1, false),

    /** Eerste Hulp Bij Ongelukken (2 jaar geldig, geen verslag) */
    EHBO(2, false),

    /** Ontruimingsoefening (geen certificaat, wel verslag vereist) */
    ONTRUIMINGSOEFENING(0, true);

    /** Aantal jaren dat het certificaat geldig is (0 = geen certificaat). */
    private final int validityYears;

    /** Of er een verslag (EvacuationReport) verplicht is bij dit trainingstype. */
    private final boolean reportRequired;

    TrainingType(int validityYears, boolean reportRequired) {
        this.validityYears = validityYears;
        this.reportRequired = reportRequired;
    }
}
