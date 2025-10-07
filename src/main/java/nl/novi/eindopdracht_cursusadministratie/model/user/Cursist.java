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

    @OneToMany(mappedBy = "cursist", cascade = CascadeType.ALL)
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "cursist", cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();
}
