package nl.novi.eindopdracht_cursusadministratie.model.certificate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

import java.time.LocalDate;

// ⬇️ deze 2 imports toevoegen:
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@NoArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificateNumber;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issuedBy;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JdbcType(BinaryJdbcType.class)
    @Column(name = "pdf_data")   // columnDefinition weghalen
    private byte[] pdfData;
}
