package nl.novi.eindopdracht_cursusadministratie.dto.certificate;

import java.time.LocalDate;

public record CertificateResponseDto(
        Long id,
        String certificateNumber,
        String courseName,
        String studentName,
        LocalDate issueDate,
        LocalDate expiryDate,
        String issuedBy
) {}
