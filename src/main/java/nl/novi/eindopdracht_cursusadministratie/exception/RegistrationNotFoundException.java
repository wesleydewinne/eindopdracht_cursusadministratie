package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een inschrijving niet wordt gevonden.
 */
public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(Long id) {
        super("Registration not found with id: " + id);
    }

    public RegistrationNotFoundException(String message) {
        super(message);
    }
}
