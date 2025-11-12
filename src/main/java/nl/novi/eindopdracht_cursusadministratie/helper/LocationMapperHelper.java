package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.dto.location.CreateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationAdminResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationStudentResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.LocationTrainerResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.UpdateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;

/**
 * Eenvoudige mapper-helper voor Location ↔ DTO's.
 * Alle methodes zijn static, zodat ze overal herbruikbaar zijn
 * zonder een Spring-component aan te maken.
 *
 * Wordt gebruikt in controllers en services om duplicatie te voorkomen.
 */
public final class LocationMapperHelper {

    private LocationMapperHelper() {
    }

    // ============================================================
    // ENTITY CONVERSIONS (DTO → ENTITY)
    // ============================================================

    /**
     * Converteert een CreateLocationDto naar een Location-entity.
     * Wordt gebruikt bij het aanmaken van een nieuwe locatie.
     */
    public static Location toEntity(CreateLocationDto dto) {
        if (dto == null) return null;

        Location l = new Location();
        l.setCompanyName(dto.getCompanyName());
        l.setAddress(dto.getAddress());
        l.setPostalCode(dto.getPostalCode());
        l.setCity(dto.getCity());
        l.setContactPerson(dto.getContactPerson());
        l.setPhoneNumber(dto.getPhoneNumber());
        l.setEmail(dto.getEmail());
        l.setNotes(dto.getNotes());
        l.setHasFireExtinguishingFacility(dto.isHasFireExtinguishingFacility());
        l.setFirefightingArea(dto.getFirefightingArea());
        l.setSufficientClassroomSpace(dto.isSufficientClassroomSpace());
        l.setLunchProvided(dto.isLunchProvided());
        l.setTrainerCanJoinLunch(dto.isTrainerCanJoinLunch());
        return l;
    }

    /**
     * Converteert een UpdateLocationDto naar een Location-entity.
     * Wordt gebruikt bij het bijwerken van een bestaande locatie.
     */
    public static Location toEntity(UpdateLocationDto dto) {
        if (dto == null) return null;

        Location l = new Location();
        l.setCompanyName(dto.getCompanyName());
        l.setAddress(dto.getAddress());
        l.setPostalCode(dto.getPostalCode());
        l.setCity(dto.getCity());
        l.setContactPerson(dto.getContactPerson());
        l.setPhoneNumber(dto.getPhoneNumber());
        l.setEmail(dto.getEmail());
        l.setNotes(dto.getNotes());
        l.setHasFireExtinguishingFacility(dto.isHasFireExtinguishingFacility());
        l.setFirefightingArea(dto.getFirefightingArea());
        l.setSufficientClassroomSpace(dto.isSufficientClassroomSpace());
        l.setLunchProvided(dto.isLunchProvided());
        l.setTrainerCanJoinLunch(dto.isTrainerCanJoinLunch());
        return l;
    }

    // ============================================================
    // DTO CONVERSIONS (ENTITY → DTO)
    // ============================================================

    /**
     * Volledige DTO voor admins (bevat alle velden).
     */
    public static LocationAdminResponseDto toAdminDto(Location l) {
        if (l == null) return null;

        LocationAdminResponseDto dto = new LocationAdminResponseDto();
        dto.setId(l.getId());
        dto.setCompanyName(l.getCompanyName());
        dto.setAddress(l.getAddress());
        dto.setPostalCode(l.getPostalCode());
        dto.setCity(l.getCity());
        dto.setContactPerson(l.getContactPerson());
        dto.setPhoneNumber(l.getPhoneNumber());
        dto.setEmail(l.getEmail());
        dto.setNotes(l.getNotes());
        dto.setHasFireExtinguishingFacility(l.isHasFireExtinguishingFacility());
        dto.setFirefightingArea(l.getFirefightingArea());
        dto.setSufficientClassroomSpace(l.isSufficientClassroomSpace());
        dto.setLunchProvided(l.isLunchProvided());
        dto.setTrainerCanJoinLunch(l.isTrainerCanJoinLunch());
        return dto;
    }

    /**
     * Vereenvoudigde DTO voor trainers (alleen relevante locatie-info).
     */
    public static LocationTrainerResponseDto toTrainerDto(Location l) {
        if (l == null) return null;

        LocationTrainerResponseDto dto = new LocationTrainerResponseDto();
        dto.setId(l.getId());
        dto.setCompanyName(l.getCompanyName());
        dto.setAddress(l.getAddress());
        dto.setPostalCode(l.getPostalCode());
        dto.setCity(l.getCity());
        dto.setNotes(l.getNotes());
        dto.setHasFireExtinguishingFacility(l.isHasFireExtinguishingFacility());
        dto.setFirefightingArea(l.getFirefightingArea());
        dto.setSufficientClassroomSpace(l.isSufficientClassroomSpace());
        dto.setLunchProvided(l.isLunchProvided());
        dto.setTrainerCanJoinLunch(l.isTrainerCanJoinLunch());
        return dto;
    }

    /**
     * Simpele DTO voor cursisten (alleen adresinformatie).
     */
    public static LocationStudentResponseDto toStudentDto(Location l) {
        if (l == null) return null;

        return new LocationStudentResponseDto(
                l.getCompanyName(),
                l.getAddress(),
                l.getPostalCode(),
                l.getCity()
        );
    }
}
