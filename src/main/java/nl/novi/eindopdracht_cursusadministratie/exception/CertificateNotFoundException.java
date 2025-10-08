package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een certificaat niet wordt gevonden.
 */
public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(Long id) {
        super("Certificate not found with id: " + id);
    }

    public CertificateNotFoundException(String message) {
        super(message);
    }
}
