package nl.novi.eindopdracht_cursusadministratie.service.certificate;

import nl.novi.eindopdracht_cursusadministratie.exception.CertificateNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CourseNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.exception.CursistNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.helper.CertificateNumberHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.DateHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.pdf.PdfCertificateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PdfCertificateService pdfCertificateService;

    public CertificateService(CertificateRepository certificateRepository,
                              UserRepository userRepository,
                              CourseRepository courseRepository,
                              PdfCertificateService pdfCertificateService) {
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.pdfCertificateService = pdfCertificateService;
    }

    // ================================================================
    //  ALLE CERTIFICATEN OPHALEN
    // ================================================================
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    // ================================================================
    //  CERTIFICAAT OPHALEN OP ID
    // ================================================================
    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new CertificateNotFoundException(id));
    }

    // ================================================================
    //  CERTIFICAAT AANMAKEN + PDF OPSLAAN BIJ CURSIST
    // ================================================================
    public Certificate createCertificate(Long studentId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        User foundUser = userRepository.findById(studentId)
                .orElseThrow(() -> new CursistNotFoundException(studentId));


        if (!(foundUser instanceof Cursist)) {
            throw new IllegalArgumentException("Gebruiker met ID " + studentId + " is geen cursist.");
        }

        Cursist student = (Cursist) foundUser;


        if (!student.hasCompletedCourse(course)) {
            throw new CursistNotFoundException(
                    "Cursist " + student.getName() +
                            " heeft de cursus '" + course.getName() + "' nog niet voltooid."
            );
        }


        if (course.getType() == TrainingType.ONTRUIMINGSOEFENING) {
            throw new IllegalArgumentException("No certificate is issued for evacuation drills.");
        }

        LocalDate today = LocalDate.now();
        Certificate certificate = new Certificate();
        certificate.setCertificateNumber(CertificateNumberHelper.generateCertificateNumber());
        certificate.setIssueDate(today);
        certificate.setExpiryDate(DateHelper.calculateExpiryDate(today, course.getType()));
        certificate.setIssuedBy("BHV Training");
        certificate.setStudent(student);
        certificate.setCourse(course);

        certificate.setPdfData(pdfCertificateService.generateCertificatePdf(certificate));

        return certificateRepository.save(certificate);
    }

    // ================================================================
    //  CERTIFICAAT VERWIJDEREN
    // ================================================================
    public void deleteCertificate(Long id) {
        if (!certificateRepository.existsById(id)) {
            throw new CertificateNotFoundException(id);
        }
        certificateRepository.deleteById(id);
    }
}
