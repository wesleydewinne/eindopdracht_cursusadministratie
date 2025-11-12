package nl.novi.eindopdracht_cursusadministratie.dto.location;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLocationDto {

    @NotBlank(message = "Bedrijfsnaam is verplicht.")
    private String companyName;

    @NotBlank(message = "Adres is verplicht.")
    private String address;

    @NotBlank(message = "Postcode is verplicht.")
    private String postalCode;

    @NotBlank(message = "Plaats is verplicht.")
    private String city;

    private String contactPerson;

    @Email
    private String email;

    private String phoneNumber;
    private String notes;
    private boolean hasFireExtinguishingFacility;
    private String firefightingArea;
    private boolean sufficientClassroomSpace;
    private boolean lunchProvided;
    private boolean trainerCanJoinLunch;
}
