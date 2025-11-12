package nl.novi.eindopdracht_cursusadministratie.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO voor Cursisten — alleen publieke locatiegegevens.
 */
@Getter
@Setter
@NoArgsConstructor
public class LocationStudentResponseDto {

    private Long id;
    private String companyName;
    private String address;
    private String postalCode;
    private String city;

    /** Gebruikt in CourseController (alleen id + bedrijfsnaam). */
    public LocationStudentResponseDto(Long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    /** Gebruikt in LocationMapperHelper (volledig adres, geen id nodig). */
    public LocationStudentResponseDto(String companyName, String address, String postalCode, String city) {
        this.companyName = companyName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }
}
