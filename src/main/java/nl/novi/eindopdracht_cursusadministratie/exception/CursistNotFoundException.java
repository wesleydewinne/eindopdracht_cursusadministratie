package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een cursist niet wordt gevonden.
 */
public class CursistNotFoundException extends RuntimeException {

    public CursistNotFoundException(Long id) {
        super("Cursist not found with id: " + id);
    }

    public CursistNotFoundException(String message) {
        super(message);
    }
}