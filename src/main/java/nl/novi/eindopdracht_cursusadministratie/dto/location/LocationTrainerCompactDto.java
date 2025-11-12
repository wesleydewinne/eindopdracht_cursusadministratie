package nl.novi.eindopdracht_cursusadministratie.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Compacte DTO voor weergave van locatie-informatie in het overzicht van alle cursussen.
 * Alleen basisgegevens worden getoond.
 */
@Getter
@AllArgsConstructor
public class LocationTrainerCompactDto {
    private Long id;
    private String companyName;
    private String address;
    private String postalCode;
    private String city;
}
