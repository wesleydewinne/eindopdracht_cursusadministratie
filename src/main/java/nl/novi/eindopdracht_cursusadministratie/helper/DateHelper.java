package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import java.time.LocalDate;

public class DateHelper {

    public static LocalDate calculateExpiryDate(LocalDate issueDate, TrainingType type) {
        return issueDate.plusYears(type.getGeldigheidInJaren());
    }
}
