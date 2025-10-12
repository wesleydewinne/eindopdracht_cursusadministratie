package nl.novi.eindopdracht_cursusadministratie.model.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

@Entity
@Table(name = "evacuation_reports")
@Getter
@Setter
@NoArgsConstructor
public class EvacuationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private EvacuationPhase phase;

    private int evacuationTimeMinutes;
    private String buildingSize;

    private String observations;
    private String improvements;
    private String evaluationAdvice;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private boolean visibleForStudents = false;

    @Lob
    @Column(name = "pdf_data", columnDefinition = "BYTEA")
    private byte[] pdfData;
}
