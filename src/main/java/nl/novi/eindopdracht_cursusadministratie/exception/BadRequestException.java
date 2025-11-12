package nl.novi.eindopdracht_cursusadministratie.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
