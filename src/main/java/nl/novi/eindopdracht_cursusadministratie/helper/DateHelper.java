package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import java.time.LocalDate;

/**
 * De {@code DateHelper} biedt hulpfuncties voor het werken met datums
 * binnen het certificaatsysteem.
 * <p>
 * Deze klasse wordt voornamelijk gebruikt om de vervaldatum van een certificaat
 * te berekenen op basis van het trainingstype en de uitgiftedatum.
 */
public class DateHelper {

    /**
     * Berekent de vervaldatum van een certificaat op basis van het type training.
     * <p>
     * Bijvoorbeeld: een BHV-certificaat is 1 jaar geldig, terwijl EHBO 2 jaar geldig is.
     *
     * @param issueDate de datum waarop het certificaat is uitgegeven
     * @param type      het trainingstype (bevat de geldigheidsduur in jaren)
     * @return de berekende vervaldatum als {@link LocalDate}
     */
    public static LocalDate calculateExpiryDate(LocalDate issueDate, TrainingType type) {
        return issueDate.plusYears(type.getGeldigheidInJaren());
    }
}
