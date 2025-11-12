package nl.novi.eindopdracht_cursusadministratie.exception;

public class CapacityExceededException extends RuntimeException {

  public CapacityExceededException(String message) {
      super(message);
  }
}
