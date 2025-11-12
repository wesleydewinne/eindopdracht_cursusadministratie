package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;

/**
 * Helperklasse voor het toepassen van herbruikbare logica
 * rondom ontruimingsverslagen, zoals het instellen van velden
 * en het genereren van automatisch evaluatieadvies.
 */
public class EvacuationReportHelper {

    /**
     * Kopieert rapportgegevens van de input naar het doelrapport
     * en genereert een evaluatieadvies via de EvacuationHelper.
     *
     * @param report     Het doelrapport dat wordt opgeslagen of bijgewerkt
     * @param inputData  De invoergegevens met nieuwe waarden
     */
    public static void applyReportDetails(EvacuationReport report, EvacuationReport inputData) {

        report.setPhase(inputData.getPhase());
        report.setEvacuationTimeMinutes(inputData.getEvacuationTimeMinutes());
        report.setBuildingSize(inputData.getBuildingSize());
        report.setObservations(inputData.getObservations());
        report.setImprovements(inputData.getImprovements());


        String advies = EvacuationHelper.generateEvaluationAdvice(
                inputData.getPhase(),
                inputData.getEvacuationTimeMinutes(),
                inputData.getBuildingSize()
        );


        Integer minutes = inputData.getEvacuationTimeMinutes();
        if (minutes != null && minutes >= 0) {
            boolean binnenTijd = EvacuationHelper.isWithinTimeLimit(
                    inputData.getPhase(),
                    minutes,
                    inputData.getBuildingSize()
            );

            if (!binnenTijd) {
                advies += " (Let op: evacuatie duurde langer dan de richttijd)";
            }
        }

        report.setEvaluationAdvice(advies);
    }
}
