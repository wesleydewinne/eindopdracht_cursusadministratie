package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een trainer niet wordt gevonden.
 */
public class TrainerNotFoundException extends RuntimeException {

    public TrainerNotFoundException(Long id) {
        super("Trainer not found with id: " + id);
    }

    public TrainerNotFoundException(String message) {
        super(message);
    }
}
