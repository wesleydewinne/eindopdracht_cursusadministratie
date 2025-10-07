package nl.novi.eindopdracht_cursusadministratie.dto.certificate;

/**
 * Request DTO voor het genereren van een certificaat.
 * Wordt gebruikt door trainers om een certificaat toe te voegen voor een cursist.
 */
public record GenerateCertificateRequest(Long courseId, Long studentId, String issuedBy) {}
