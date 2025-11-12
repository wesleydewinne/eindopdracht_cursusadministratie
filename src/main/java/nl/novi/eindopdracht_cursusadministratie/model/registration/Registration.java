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

    @Column(nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    @Column(nullable = false)
    private boolean present = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RegistrationStatus status = RegistrationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    /**
     * Trainer markeert aanwezigheid.
     * Alleen bij aanwezigheid (true) blijft status PENDING.
     * Afwezigen worden pas later op ABSENT gezet bij afronden.
     */
    public void updateAttendance(boolean present) {
        this.present = present;

        if (present && this.status == RegistrationStatus.PENDING) {
            this.status = RegistrationStatus.PENDING; // blijft PENDING tot beoordeling
        }
    }

    /** Markering na afronden cursus */
    public void markCompleted() {
        this.status = RegistrationStatus.COMPLETED;
    }

    public boolean isApproved() {
        return this.status == RegistrationStatus.APPROVED;
    }

    public boolean isAbsent() {
        return this.status == RegistrationStatus.ABSENT;
    }

    public boolean isPending() {
        return this.status == RegistrationStatus.PENDING;
    }
}
