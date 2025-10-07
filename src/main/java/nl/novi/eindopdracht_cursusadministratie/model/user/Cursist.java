package nl.novi.eindopdracht_cursusadministratie.model.user;

import jakarta.persistence.*;
import lombok.*;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("CURSIST")
public class Cursist extends User {

    private boolean active = true;

    // Owning side in Certificate is 'student'
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Certificate> certificates = new ArrayList<>();

    // Owning side in Registration is 'student'
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();
}
