package nl.novi.eindopdracht_cursusadministratie.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO voor Admins — volledige locatie-informatie inclusief contactgegevens.
 */
@Getter
@Setter
@NoArgsConstructor
public class LocationAdminResponseDto extends LocationTrainerResponseDto {

    private String contactPerson;
    private String phoneNumber;
    private String email;

    /** Convenience-constructor voor gebruik in CourseController. */
    public LocationAdminResponseDto(Long id, String companyName, String address) {
        super(id, companyName);
        this.setAddress(address);
    }
}
