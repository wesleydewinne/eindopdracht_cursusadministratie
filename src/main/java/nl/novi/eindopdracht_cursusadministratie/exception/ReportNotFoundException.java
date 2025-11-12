package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een ontruimingsverslag (EvacuationReport)
 * niet wordt gevonden in de database.
 */
public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(Long id) {
        super("Ontruimingsverslag niet gevonden met id: " + id);
    }

    public ReportNotFoundException(String message) {
        super(message);
    }
}
