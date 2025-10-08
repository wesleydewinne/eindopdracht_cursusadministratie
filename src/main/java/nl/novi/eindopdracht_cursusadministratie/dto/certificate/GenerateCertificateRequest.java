package nl.novi.eindopdracht_cursusadministratie.dto.certificate;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO voor het genereren van een certificaat.
 * Wordt gebruikt door trainers om een certificaat toe te voegen voor een cursist.
 */
public record GenerateCertificateRequest(
       @NotNull Long courseId,
       @NotNull Long studentId,
       @NotNull String issuedBy
) {}
