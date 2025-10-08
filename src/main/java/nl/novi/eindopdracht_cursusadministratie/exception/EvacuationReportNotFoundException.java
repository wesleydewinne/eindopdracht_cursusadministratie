package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een ontruimingsverslag niet wordt gevonden.
 */
public class EvacuationReportNotFoundException extends RuntimeException {

    public EvacuationReportNotFoundException(Long id) {
        super("Evacuation report not found with id: " + id);
    }

    public EvacuationReportNotFoundException(String message) {
        super(message);
    }
}
