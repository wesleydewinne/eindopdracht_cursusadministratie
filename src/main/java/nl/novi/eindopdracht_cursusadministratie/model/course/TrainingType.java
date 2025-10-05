package nl.novi.eindopdracht_cursusadministratie.model.course;

import lombok.Getter;

@Getter
public enum TrainingType {
    BHV(1),
    EHBO(2),
    ONTRUIMINGSOEFENING(0);

    private final int geldigheidInJaren;

    TrainingType(int geldigheidInJaren) {
        this.geldigheidInJaren = geldigheidInJaren;
    }
}
