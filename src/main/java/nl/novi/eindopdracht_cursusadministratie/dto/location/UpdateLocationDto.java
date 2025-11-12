package nl.novi.eindopdracht_cursusadministratie.dto.location;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLocationDto {

    @NotBlank(message = "Bedrijfsnaam is verplicht.")
    private String companyName;

    private String address;
    private String postalCode;
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
