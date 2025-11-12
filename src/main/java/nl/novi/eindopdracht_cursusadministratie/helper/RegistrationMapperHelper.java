package nl.novi.eindopdracht_cursusadministratie.helper;

import lombok.experimental.UtilityClass;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationStatusResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;

/**
 * Utility helper class voor het omzetten (mappen) van Registration-entiteiten
 * naar verschillende DTO-typen afhankelijk van de context.
 *
 * Bevat uitsluitend statische methoden — er mogen geen instanties van worden gemaakt.
 */
@UtilityClass
public class RegistrationMapperHelper {

    /**
     * Maakt een compacte weergave voor trainers of admins.
     * Wordt gebruikt in overzichten (bijv. alle inschrijvingen per trainer of cursus).
     */
    public static RegistrationTrainerResponseDto toTrainerDto(Registration r) {
        if (r == null) return null;

        return new RegistrationTrainerResponseDto(
                r.getId(),
                r.getStudent() != null ? r.getStudent().getId() : null,
                r.getStudent() != null ? r.getStudent().getName() : null,
                r.isPresent(),
                r.getStatus()
        );
    }

    /**
     * Maakt een statusresponse voor trainer of admin na update van aanwezigheid/status.
     * Wordt gebruikt bij endpoints die een wijziging uitvoeren.
     */
    public static RegistrationStatusResponseDto toStatusDto(Registration r) {
        if (r == null) return null;

        return new RegistrationStatusResponseDto(
                r.getId(),
                r.getStudent() != null ? r.getStudent().getId() : null,
                r.getStudent() != null ? r.getStudent().getName() : null,
                r.isPresent(),
                r.getStatus(),
                r.getCourse() != null ? r.getCourse().getName() : null
        );
    }
}
