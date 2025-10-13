package nl.novi.eindopdracht_cursusadministratie.model.user;

import jakarta.persistence.*;
import lombok.*;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.registration.RegistrationStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Subklasse van User die een cursist representeert.
 * Bevat extra velden en relaties naar certificaten en inschrijvingen.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CURSIST")
public class Cursist extends User {

    private boolean active = true;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public boolean hasCompletedCourse(Course course) {
        if (registrations == null || registrations.isEmpty()) {
            return false;
        }

        return registrations.stream()
                .anyMatch(reg -> reg.getCourse().equals(course)
                        && reg.getStatus() == RegistrationStatus.COMPLETED);
    }
}
