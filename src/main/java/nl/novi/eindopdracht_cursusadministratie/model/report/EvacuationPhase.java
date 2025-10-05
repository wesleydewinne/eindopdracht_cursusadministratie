package nl.novi.eindopdracht_cursusadministratie.model.report;

import lombok.Getter;

@Getter
public enum EvacuationPhase {
    TABLETOP(0, "Tabletop-oefening"),
    SMALL_SCENARIO(1, "Kleine scenario-oefening"),
    ANNOUNCED_EVACUATION(2, "Volledige ontruiming (aangekondigd)"),
    UNANNOUNCED_EVACUATION(3, "Volledige ontruiming (onaangekondigd)"),
    UNANNOUNCED_WITH_VICTIMS(4, "Onaangekondigd ontruiming met slachtoffers");

    private final int level;
    private final String description;

    EvacuationPhase(int level, String description) {
        this.level = level;
        this.description = description;
    }
}
