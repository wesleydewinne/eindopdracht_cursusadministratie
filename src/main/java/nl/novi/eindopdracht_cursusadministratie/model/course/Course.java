package nl.novi.eindopdracht_cursusadministratie.model.course;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

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

    @Future(message = "De startdatum moet in de toekomst liggen")
    private LocalDate startDate;
    private LocalDate endDate;

    @Positive(message = "Het maximum aantal deelnemers moet positief zijn")
    private Integer maxParticipants;

    /**
     * Geeft aan of een administrator mag toestaan dat het maximum aantal deelnemers wordt overschreden.
     * Wordt gebruikt in de inschrijvingslogica.
     */
    @Column(name = "admin_override_allowed", nullable = false)
    private boolean adminOverrideAllowed = false;

    @Enumerated(EnumType.STRING)
    private TrainingType type;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private EvacuationPhase phase;

    private boolean reportRequired;
}
