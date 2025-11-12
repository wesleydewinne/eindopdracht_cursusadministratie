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

    private static final int SMALL_LIMIT_MIN  = 4;
    private static final int MEDIUM_LIMIT_MIN = 7;
    private static final int LARGE_LIMIT_MIN  = 10;

    /** Overload die ook met null kan omgaan voor minuten. */
    public static String generateEvaluationAdvice(EvacuationPhase phase, Integer evacuationTimeMinutes, String buildingSize) {
        int minutes = (evacuationTimeMinutes == null || evacuationTimeMinutes < 0) ? -1 : evacuationTimeMinutes;
        return generateEvaluationAdvice(phase, minutes, buildingSize);
    }

    /**
     * Genereert automatisch evaluatieadvies.
     *
     * @param phase         oefenfase
     * @param minutes       evacuatie-tijd in minuten (>= 0)
     * @param buildingSize  gebouwgrootte (SMALL/KLEIN, MEDIUM/MIDDEL, LARGE/GROOT).
     *                      Alleen vereist bij fase 2–4.
     * @return tekstueel advies
     */
    public static String generateEvaluationAdvice(EvacuationPhase phase, int minutes, String buildingSize) {
        if (phase == null) {
            return "Geen fase geselecteerd – evaluatie niet mogelijk.";
        }

        if (minutes < 0) {
            return "Geen geldige evacuatie-tijd opgegeven (minuten) – evaluatie niet mogelijk.";
        }

        String normalizedSize = normalizeBuildingSize(buildingSize);
        if ("UNKNOWN".equals(normalizedSize) &&
                (phase == EvacuationPhase.ANNOUNCED_EVACUATION ||
                        phase == EvacuationPhase.UNANNOUNCED_EVACUATION ||
                        phase == EvacuationPhase.UNANNOUNCED_WITH_VICTIMS)) {
            return "Geen gebouwgrootte opgegeven – evaluatie niet mogelijk voor deze fase.";
        }

        int limit = getMaxTimeLimitMinutes(normalizedSize);
        boolean within = minutes <= limit;

        String omschrijving = EvacuationPhaseHelper.getOmschrijving(phase);

        if (phase == EvacuationPhase.ANNOUNCED_EVACUATION ||
                phase == EvacuationPhase.UNANNOUNCED_EVACUATION ||
                phase == EvacuationPhase.UNANNOUNCED_WITH_VICTIMS) {

            String tijdsadvies = within
                    ? String.format(" (%.0f/%d min, %s)", (double) minutes, limit, normalizedSize.toLowerCase())
                    : String.format(" – overschreed de tijdslimiet (%d min, limiet %d min voor %s).",
                    minutes, limit, normalizedSize.toLowerCase());
            return omschrijving + tijdsadvies;
        }

        return omschrijving;
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
            default -> MEDIUM_LIMIT_MIN;
        };
    }

    /** Hulpmethode: is de opgegeven tijd binnen limiet? (alleen relevant voor fases 2–4) */
    public static boolean isWithinTimeLimit(EvacuationPhase phase, int minutes, String buildingSize) {
        if (phase == EvacuationPhase.TABLETOP || phase == EvacuationPhase.SMALL_SCENARIO) return true;
        String normalized = normalizeBuildingSize(buildingSize);
        if ("UNKNOWN".equals(normalized) || minutes < 0) return false;
        return minutes <= getMaxTimeLimitMinutes(normalized);
    }

    // ✅ Je oorspronkelijke switch is hier intact gehouden
    public static class EvacuationPhaseHelper {
        public static String getOmschrijving(EvacuationPhase phase) {
            switch (phase) {
                case TABLETOP:
                    return "Tabletop-oefening: focus op samenwerking, procedures en duidelijke taakverdeling binnen het BHV-team.";
                case SMALL_SCENARIO:
                    return "Kleine scenario-oefening: beoordeel snelheid van handelen, communicatie en taakverdeling in een kleinschalige setting.";
                case ANNOUNCED_EVACUATION:
                    return "Aangekondigde ontruiming: test volledige gebouwontruiming met nadruk op voorbereiding, alarmering en evaluatie van de procedures.";
                case UNANNOUNCED_EVACUATION:
                    return "Onaangekondigde ontruiming: beoordeel realistische reacties, besluitvorming en coördinatie zonder voorafgaande waarschuwing.";
                case UNANNOUNCED_WITH_VICTIMS:
                    return "Onaangekondigde ontruiming met slachtoffers: beoordeel inzet van hulpverlening, levensreddende handelingen en nazorg onder druk.";
                default:
                    return "Onbekende fase: controleer de ingevoerde waarde van 'phase'.";
            }
        }
    }
}
