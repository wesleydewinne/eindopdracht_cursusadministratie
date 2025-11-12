package nl.novi.eindopdracht_cursusadministratie.model.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;
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

    @OneToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
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
    @Basic(fetch = FetchType.LAZY)
    @JdbcType(BinaryJdbcType.class)
    @Column(name = "pdf_data")
    private byte[] pdfData;
}
