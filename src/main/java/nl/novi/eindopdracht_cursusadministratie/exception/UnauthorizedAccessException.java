package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Wordt gegooid wanneer een gebruiker probeert een actie uit te voeren
 * waarvoor hij of zij geen rechten heeft (autorisatiefout).
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
