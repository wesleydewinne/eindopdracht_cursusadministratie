package nl.novi.eindopdracht_cursusadministratie.model.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;

import java.time.LocalDate;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    private TrainingType type;

    // Relatie: één trainer geeft meerdere cursussen
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    // Relatie: elke cursus heeft één locatie
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
