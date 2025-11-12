package nl.novi.eindopdracht_cursusadministratie.model.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Future(message = "De startdatum moet in de toekomst liggen")
    private LocalDate startDate;

    private LocalDate endDate;

    @Positive(message = "Het maximum aantal deelnemers moet positief zijn")
    private Integer maxParticipants;

    @Column(name = "admin_override_allowed", nullable = false)
    private boolean adminOverrideAllowed = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingType type;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @JsonBackReference
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private EvacuationPhase phase;

    private boolean reportRequired;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private EvacuationReport evacuationReport;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    // Hulpmethoden

    public boolean isEvacuationTraining() {
        return this.type == TrainingType.ONTRUIMINGSOEFENING;
    }

    public boolean requiresReport() {
        if (this.type != TrainingType.ONTRUIMINGSOEFENING) {
            return false;
        }
        return this.phase == EvacuationPhase.ANNOUNCED_EVACUATION
                || this.phase == EvacuationPhase.UNANNOUNCED_EVACUATION
                || this.phase == EvacuationPhase.UNANNOUNCED_WITH_VICTIMS;
    }
}
