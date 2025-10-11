package nl.novi.eindopdracht_cursusadministratie.model.certificate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;

import java.time.LocalDate;

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

    //  Relatie met de cursist
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    //  Relatie met de cursus
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Lob
    @Column(name = "pdf_data", columnDefinition = "BYTEA")
    private byte[] pdfData;
}
