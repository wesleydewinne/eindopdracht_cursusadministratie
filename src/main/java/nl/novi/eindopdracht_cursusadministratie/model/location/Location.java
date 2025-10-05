package nl.novi.eindopdracht_cursusadministratie.model.location;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String address;
    private String postalCode;
    private String city;

    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String notes;

    private boolean hasFireExtinguishingFacility;
    private String firefightingArea;

    private boolean sufficientClassroomSpace;
    private boolean lunchProvided;
    private boolean trainerCanJoinLunch;
}
