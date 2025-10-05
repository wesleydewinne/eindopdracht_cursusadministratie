package nl.novi.eindopdracht_cursusadministratie.service.certificate;

import nl.novi.eindopdracht_cursusadministratie.helper.CertificateNumberHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.DateHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public CertificateService(CertificateRepository certificateRepository,
                              UserRepository userRepository,
                              CourseRepository courseRepository) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    //  Alle certificaten ophalen
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    //  Certificaat ophalen op ID
    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Certificate not found with ID: " + id));
    }

    //  Certificaat aanmaken
    public Certificate createCertificate(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        // Alleen certificaat als het GEEN ontruimingsoefening is
        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalArgumentException("No certificate is issued for evacuation drills.");
        }

        Certificate certificate = new Certificate();
        certificate.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        certificate.setIssueDate(LocalDate.now());
        certificate.setExpiryDate(DateHelper.calculateExpiryDate(LocalDate.now(), course.getType()));
        certificate.setIssuedBy("Safety First BV");
        certificate.setStudent(student);
        certificate.setCourse(course);

        return certificateRepository.save(certificate);
    }

    //  Certificaat verwijderen
    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }
}
