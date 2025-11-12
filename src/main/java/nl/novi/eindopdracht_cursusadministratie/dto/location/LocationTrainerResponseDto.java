package nl.novi.eindopdracht_cursusadministratie.dto.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO voor Trainers — bevat locatie-informatie die relevant is voor trainingsplanning.
 *
 * Gebruik:
 *  - Compacte constructor → voor overzichtslijsten (alle cursussen van trainer)
 *  - Uitgebreide constructor → voor detailweergave van één cursus
 */
@Getter
@Setter
@NoArgsConstructor
public class LocationTrainerResponseDto {

    private Long id;
    private String companyName;
    private String address;
    private String postalCode;
    private String city;

    // Extra velden die alleen worden gebruikt in detailweergave
    private String notes;
    private boolean hasFireExtinguishingFacility;
    private String firefightingArea;
    private boolean sufficientClassroomSpace;
    private boolean lunchProvided;
    private boolean trainerCanJoinLunch;

    /**
     * Compacte constructor — gebruikt in cursusoverzicht
     * (trainer of cursist ziet alleen basisinformatie).
     */
    public LocationTrainerResponseDto(Long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    /**
     * Uitgebreide constructor — gebruikt in detailweergave van één cursus
     * (trainer ziet ook adres, stad en postcode).
     */
    public LocationTrainerResponseDto(
            Long id,
            String companyName,
            String address,
            String postalCode,
            String city
    ) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }
}
