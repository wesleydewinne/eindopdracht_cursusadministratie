package nl.novi.eindopdracht_cursusadministratie.helper;

import lombok.experimental.UtilityClass;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;

/**
 * Utility helper class met herbruikbare logica rondom inschrijvingen (registraties).
 * <p>
 * Bevat statusbepaling op basis van aanwezigheid en huidige status.
 * Wordt aangeroepen na updateAttendance() in de RegistrationService.
 */
@UtilityClass
public class RegistrationHelper {

    /**
     * Controleert en past de inschrijvingsstatus aan op basis van aanwezigheid en bestaande status.
     * <ul>
     *     <li>Als de cursist aanwezig is en de status is PENDING of APPROVED → COMPLETED</li>
     *     <li>Als de cursist afwezig is → CANCELLED</li>
     * </ul>
     *
     * @param registration de registratie waarvoor de status moet worden geëvalueerd
     */
    public static void evaluateCompletion(Registration registration) {
        if (registration == null) return;

        RegistrationStatus currentStatus = registration.getStatus();
        if (currentStatus == null) {
            registration.setStatus(RegistrationStatus.PENDING);
        }

        if (registration.isPresent()) {
            if (currentStatus == RegistrationStatus.PENDING || currentStatus == RegistrationStatus.APPROVED) {
                registration.setStatus(RegistrationStatus.COMPLETED);
            }
        } else {
            registration.setStatus(RegistrationStatus.CANCELLED);
        }
    }
}
