package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een locatie niet wordt gevonden.
 */
public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(Long id) {
        super("Location not found with id: " + id);
    }

    public LocationNotFoundException(String message) {
        super(message);
    }
}