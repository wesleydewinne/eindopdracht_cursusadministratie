package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;

public class EvacuationHelper {

    /**
     * Genereert automatisch een advies gebaseerd op fase, tijd en gebouwgrootte.
     * @param phase - de oefenfase
     * @param evacuationTimeMinutes - duur van de evacuatie in minuten
     * @param buildingSize - grootte van het gebouw ("SMALL", "MEDIUM", "LARGE")
     * @return automatisch adviesbericht
     */
    public static String generateEvaluationAdvice(EvacuationPhase phase, double evacuationTimeMinutes, String buildingSize) {
        if (phase == null) {
            return "Geen fase geselecteerd – kan geen evaluatie genereren.";
        }

        // Standaardtijdslimieten afhankelijk van gebouwgrootte
        double allowedTime;
        switch (buildingSize.toUpperCase()) {
            case "SMALL" -> allowedTime = 4.0;
            case "MEDIUM" -> allowedTime = 5.0;
            case "LARGE" -> allowedTime = 6.0;
            default -> allowedTime = 5.0; // fallback
        }

        switch (phase) {
            case TABLETOP:
                return "Goede voorbereidingsoefening. Focus op communicatie tussen teams.";

            case SMALL_SCENARIO:
                return "Kleine oefening uitgevoerd. Controleer opvolging van procedures.";

            case ANNOUNCED_EVACUATION:
                if (evacuationTimeMinutes <= allowedTime + 1) {
                    return "Aangekondigde oefening goed uitgevoerd binnen verwachte tijd.";
                } else {
                    return "Oefening duurde te lang – verbeter coördinatie en voorbereiding.";
                }

            case UNANNOUNCED_EVACUATION:
                if (evacuationTimeMinutes <= allowedTime) {
                    return "Zeer goed – snelle reactie op onverwachte situatie.";
                } else {
                    return "Redelijke uitvoering, maar reactietijd kan beter.";
                }

            case UNANNOUNCED_WITH_VICTIMS:
                if (evacuationTimeMinutes <= allowedTime + 1) {
                    return "Uitstekende prestatie onder moeilijke omstandigheden!";
                } else {
                    return "Evacuatie duurde te lang – verbeter slachtofferopvang en communicatie.";
                }

            default:
                return "Geen advies beschikbaar voor deze fase.";
        }
    }
}
