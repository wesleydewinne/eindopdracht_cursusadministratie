package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;

/**
 * De {@code EvacuationHelper} genereert evaluatie-adviezen op basis van
 * oefenfase, evacuatie-tijd (in minuten) en – waar relevant – gebouwgrootte.
 * <p>
 * Gebouwgrootte wordt alleen toegepast voor fases:
 * ANNOUNCED_EVACUATION, UNANNOUNCED_EVACUATION, UNANNOUNCED_WITH_VICTIMS.
 */
public class EvacuationHelper {

    // Richtwaarden per gebouwgrootte (minuten)
    private static final int SMALL_LIMIT_MIN  = 4;
    private static final int MEDIUM_LIMIT_MIN = 7;
    private static final int LARGE_LIMIT_MIN  = 10;

    /**
     * Overload die ook met null kan omgaan voor minuten.
     * Delegereert naar de int-variant met een guard.
     */
    public static String generateEvaluationAdvice(EvacuationPhase phase, Integer evacuationTimeMinutes, String buildingSize) {
        int minutes = (evacuationTimeMinutes == null || evacuationTimeMinutes < 0) ? -1 : evacuationTimeMinutes;
        return generateEvaluationAdvice(phase, minutes, buildingSize);
    }

    /**
     * Genereert automatisch evaluatieadvies.
     *
     * @param phase   oefenfase
     * @param minutes evacuatie-tijd in minuten (>= 0)
     * @param buildingSize gebouwgrootte (SMALL/KLEIN, MEDIUM/MIDDEL, LARGE/GROOT). Alleen vereist bij fase 2–4.
     * @return tekstueel advies
     */
    public static String generateEvaluationAdvice(EvacuationPhase phase, int minutes, String buildingSize) {
        if (phase == null) {
            return "Geen fase geselecteerd – evaluatie niet mogelijk.";
        }

        // Voor TABLETOP en SMALL_SCENARIO negeren we gebouwgrootte en tijdslimieten
        switch (phase) {
            case TABLETOP:
                return "Tabletop-oefening: focus op samenwerking, procedures en duidelijke taakverdeling.";
            case SMALL_SCENARIO:
                return "Kleine scenario-oefening: beoordeel snelheid van handelen en communicatie in het team.";
            default:
                // Verdergaan voor fases 2–4 (gebouwgrootte is relevant)
        }

        if (minutes < 0) {
            return "Geen geldige evacuatie-tijd opgegeven (minuten) – evaluatie niet mogelijk.";
        }

        String normalizedSize = normalizeBuildingSize(buildingSize);
        if ("UNKNOWN".equals(normalizedSize)) {
            return "Geen gebouwgrootte opgegeven – evaluatie niet mogelijk voor deze fase.";
        }

        int limit = getMaxTimeLimitMinutes(normalizedSize);
        boolean within = minutes <= limit;

        switch (phase) {
            case ANNOUNCED_EVACUATION:
                return within
                        ? String.format("Aangekondigde ontruiming verliep goed (%.0f/%d min, %s).",
                        (double) minutes, limit, normalizedSize.toLowerCase())
                        : String.format("Aangekondigde ontruiming duurde te lang (%d min, limiet %d min voor %s). Verbeter voorbereiding en coördinatie.",
                        minutes, limit, normalizedSize.toLowerCase());

            case UNANNOUNCED_EVACUATION:
                return within
                        ? String.format("Onverwachte ontruiming uitstekend uitgevoerd – snelle reactie (%d/%d min, %s).",
                        minutes, limit, normalizedSize.toLowerCase())
                        : String.format("Onverwachte ontruiming duurde te lang (%d min, limiet %d min voor %s). Train reactietijd en verzamelprocedure.",
                        minutes, limit, normalizedSize.toLowerCase());

            case UNANNOUNCED_WITH_VICTIMS:
                return within
                        ? String.format("Zeer goede prestatie bij slachtoffer-scenario (%d/%d min, %s).",
                        minutes, limit, normalizedSize.toLowerCase())
                        : String.format("Evacuatie met slachtoffers duurde te lang (%d min, limiet %d min voor %s). Oefen communicatie en opvang beter.",
                        minutes, limit, normalizedSize.toLowerCase());

            default:
                // zou niet bereikt moeten worden, maar voor de zekerheid:
                return "Geen advies beschikbaar.";
        }
    }

    /** Gebouwgrootte normaliseren en NL/EN varianten accepteren. */
    private static String normalizeBuildingSize(String size) {
        if (size == null) return "UNKNOWN";
        String s = size.trim().toUpperCase();
        return switch (s) {
            case "SMALL", "KLEIN", "S" -> "SMALL";
            case "MEDIUM", "MIDDEL", "M", "MIDDEN" -> "MEDIUM";
            case "LARGE", "GROOT", "L" -> "LARGE";
            default -> "UNKNOWN";
        };
    }

    /** Maximale richttijd per gebouwgrootte (minuten). */
    private static int getMaxTimeLimitMinutes(String normalizedSize) {
        return switch (normalizedSize) {
            case "SMALL" -> SMALL_LIMIT_MIN;
            case "MEDIUM" -> MEDIUM_LIMIT_MIN;
            case "LARGE" -> LARGE_LIMIT_MIN;
            default -> MEDIUM_LIMIT_MIN; // veilige default
        };
    }

    /**
     * Hulpmethode: is de opgegeven tijd binnen limiet? (alleen relevant voor fases 2–4)
     */
    public static boolean isWithinTimeLimit(EvacuationPhase phase, int minutes, String buildingSize) {
        if (phase == EvacuationPhase.TABLETOP || phase == EvacuationPhase.SMALL_SCENARIO) return true;
        String normalized = normalizeBuildingSize(buildingSize);
        if ("UNKNOWN".equals(normalized) || minutes < 0) return false;
        return minutes <= getMaxTimeLimitMinutes(normalized);
    }
}
