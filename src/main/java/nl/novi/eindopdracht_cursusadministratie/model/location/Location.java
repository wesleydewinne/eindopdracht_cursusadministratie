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

    private String companyName;  // ✅ Bedrijfsnaam waar de training wordt gegeven
    private String address;      // Straat + huisnummer
    private String postalCode;   // Postcode
    private String city;         // Plaatsnaam
}
