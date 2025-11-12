package nl.novi.eindopdracht_cursusadministratie.exception;

public class CursistNotFoundException extends RuntimeException {

    public CursistNotFoundException(Long id) {
        super("Cursist niet gevonden met id: " + id);
    }
}
