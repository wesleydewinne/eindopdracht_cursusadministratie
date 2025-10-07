package nl.novi.eindopdracht_cursusadministratie.model.registration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "registrations")
@Getter
@Setter
@NoArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate registrationDate = LocalDate.now();
    private boolean present;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status = RegistrationStatus.PENDING;

    // Elke inschrijving hoort bij één cursus
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Elke inschrijving hoort bij één cursist (user)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
}
