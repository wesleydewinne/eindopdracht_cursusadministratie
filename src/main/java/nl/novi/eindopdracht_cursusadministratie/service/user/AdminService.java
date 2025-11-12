package nl.novi.eindopdracht_cursusadministratie.service.user;

import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseCreateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseUpdateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.CreateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.UpdateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.AdminCursistCreateDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.AdminTrainerCreateDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.TrainerUpdateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.exception.*;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.report.ReportStatus;
import nl.novi.eindopdracht_cursusadministratie.model.user.*;
import nl.novi.eindopdracht_cursusadministratie.repository.certificate.CertificateRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.course.CourseRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.registration.RegistrationRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.report.EvacuationReportRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.TrainerRepository;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final LocationRepository locationRepository;
    private final CertificateRepository certificateRepository;
    private final EvacuationReportRepository reportRepository;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;

    // ============================================================
    // GEBRUIKERSBEHEER
    // ============================================================

    public List<User> getAllUsers() {
        verifyAdminRole();
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {
        verifyAdminRole();

        // Hoofdbeheerder mag niet verwijderd worden
        if (id == 1L) {
            throw new IllegalStateException("De hoofdbeheerder (ID 1) kan niet worden verwijderd.");
        }

        //  Ophalen van de gebruiker of fout gooien
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String name = user.getName();
        String roleName = user.getRole().name();

        // ============================================================
        //  TRAINER
        // ============================================================
        if (user instanceof Trainer trainer) {

            // Controleer of de trainer gekoppeld is aan cursussen
            List<Course> gekoppeldeCursussen = courseRepository.findByTrainer_Id(trainer.getId());

            if (!gekoppeldeCursussen.isEmpty()) {
                throw new IllegalStateException(
                        "Trainer '" + trainer.getName() + "' kan niet worden verwijderd omdat deze nog gekoppeld is aan "
                                + gekoppeldeCursussen.size() + " cursus(sen)."
                );
            }

            // Geen cursussen meer gekoppeld → veilig verwijderen
            userRepository.delete(trainer);
        }

        // ============================================================
        //  CURSIST
        // ============================================================
        else if (user instanceof Cursist cursist) {

            // Controleer of de cursist nog ingeschreven staat voor cursussen
            long inschrijvingen = registrationRepository.countByStudent_Id(cursist.getId());

            if (inschrijvingen > 0) {
                throw new IllegalStateException(
                        "Cursist '" + cursist.getName() + "' kan niet worden verwijderd omdat hij/zij nog is " +
                                "ingeschreven voor " + inschrijvingen + " cursus(sen)."
                );
            }

            userRepository.delete(cursist);
        }

        // ============================================================
        //  OVERIGE USERS (bijv. admin zonder subtype)
        // ============================================================

        else {
            userRepository.delete(user);
        }

        // ============================================================
        //  SUCCESBOODSCHAP
        // ============================================================
        return "Gebruiker met ID " + id + " (" + name + ", rol: " + roleName + ") is succesvol verwijderd.";
    }


    // ============================================================
    // CURSISTEN
    // ============================================================

    public List<Cursist> getAllCursisten() {
        verifyAdminRole();
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Cursist)
                .map(u -> (Cursist) u)
                .toList();
    }

    public Cursist createCursistFromDto(AdminCursistCreateDto dto) {
        verifyAdminRole();
        Cursist cursist = new Cursist();
        cursist.setName(dto.getName());
        cursist.setEmail(dto.getEmail());
        cursist.setActive(dto.isActive());
        cursist.setPassword(passwordEncoder.encode(dto.getPassword()));
        cursist.setRole(Role.CURSIST);
        return userRepository.save(cursist);
    }

    public Cursist updateCursistFromDto(Long id, AdminCursistCreateDto dto) {
        verifyAdminRole();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!(user instanceof Cursist existing)) {
            throw new IllegalArgumentException("Gebruiker met ID " + id + " is geen cursist.");
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setActive(dto.isActive());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(existing);
    }

    // ============================================================
    // TRAINERS
    // ============================================================

    public List<Trainer> getAllTrainers() {
        verifyAdminRole();
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Trainer)
                .map(u -> (Trainer) u)
                .toList();
    }

    public Trainer createTrainerFromDto(AdminTrainerCreateDto dto) {
        verifyAdminRole();
        Trainer trainer = new Trainer();
        trainer.setName(dto.getName());
        trainer.setEmail(dto.getEmail());
        trainer.setExpertise(dto.getExpertise());
        trainer.setPassword(passwordEncoder.encode(dto.getPassword()));
        trainer.setRole(Role.TRAINER);
        return userRepository.save(trainer);
    }

    public Trainer updateTrainer(Long id, TrainerUpdateRequestDto dto) {
        verifyAdminRole();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!(user instanceof Trainer existing)) {
            throw new IllegalArgumentException("Gebruiker met ID " + id + " is geen trainer.");
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setExpertise(dto.getExpertise());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(existing);
    }

    // ============================================================
    // CURSUSSEN
    // ============================================================

    public List<Course> getAllCourses() {
        verifyAdminRole();
        return courseRepository.findAll();
    }

    /**
     * Maak een nieuwe cursus aan via CourseCreateRequestDto.
     */
    public Course createCourseFromDto(CourseCreateRequestDto dto) {
        verifyAdminRole();

        Course course = new Course();
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setMaxParticipants(dto.getMaxParticipants());
        course.setAdminOverrideAllowed(dto.isAdminOverrideAllowed());
        course.setType(TrainingType.valueOf(dto.getType().toUpperCase()));
        course.setReportRequired(dto.isReportRequired());

        if (dto.getPhase() != null) {
            try {
                course.setPhase(EvacuationPhase.valueOf(dto.getPhase().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ongeldige fase: " + dto.getPhase());
            }
        }

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException(dto.getLocationId()));
        Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                .orElseThrow(() -> new TrainerNotFoundException(dto.getTrainerId()));

        course.setLocation(location);
        course.setTrainer(trainer);

        return courseService.createCourse(course);
    }

    /**
     * Werk een bestaande cursus bij via CourseUpdateRequestDto.
     */
    public Course updateCourseFromDto(Long id, CourseUpdateRequestDto dto) {
        verifyAdminRole();

        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setMaxParticipants(dto.getMaxParticipants());
        existing.setAdminOverrideAllowed(dto.isAdminOverrideAllowed());
        existing.setType(TrainingType.valueOf(dto.getType().toUpperCase()));
        existing.setReportRequired(dto.isReportRequired());

        if (dto.getPhase() != null) {
            try {
                existing.setPhase(EvacuationPhase.valueOf(dto.getPhase().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ongeldige fase: " + dto.getPhase());
            }
        }

        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new TrainerNotFoundException(dto.getTrainerId()));
            existing.setTrainer(trainer);
        }

        if (dto.getLocationId() != null) {
            Location location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new LocationNotFoundException(dto.getLocationId()));
            existing.setLocation(location);
        }

        courseService.applyTrainingTypeLogic(existing);

        return courseRepository.save(existing);
    }

    public void deleteCourse(Long id) {
        verifyAdminRole();
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // ============================================================
    // REGISTRATIES
    // ============================================================

    public void deleteRegistration(Long registrationId) {
        verifyAdminRole();
        if (!registrationRepository.existsById(registrationId)) {
            throw new IllegalArgumentException("Registratie met ID " + registrationId + " bestaat niet.");
        }
        registrationRepository.deleteById(registrationId);
    }

    // ============================================================
    // LOCATIES
    // ============================================================

    public List<Location> getAllLocations() {
        verifyAdminRole();
        return locationRepository.findAll();
    }

    public Location createLocation(CreateLocationDto dto) {
        verifyAdminRole();
        Location location = new Location();
        location.setCompanyName(dto.getCompanyName());
        location.setAddress(dto.getAddress());
        location.setPostalCode(dto.getPostalCode());
        location.setCity(dto.getCity());
        location.setContactPerson(dto.getContactPerson());
        location.setEmail(dto.getEmail());
        location.setPhoneNumber(dto.getPhoneNumber());
        location.setNotes(dto.getNotes());
        location.setHasFireExtinguishingFacility(dto.isHasFireExtinguishingFacility());
        location.setFirefightingArea(dto.getFirefightingArea());
        location.setSufficientClassroomSpace(dto.isSufficientClassroomSpace());
        location.setLunchProvided(dto.isLunchProvided());
        location.setTrainerCanJoinLunch(dto.isTrainerCanJoinLunch());
        return locationRepository.save(location);
    }

    public Location updateLocation(Long id, UpdateLocationDto dto) {
        verifyAdminRole();
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
        existing.setCompanyName(dto.getCompanyName());
        existing.setAddress(dto.getAddress());
        existing.setPostalCode(dto.getPostalCode());
        existing.setCity(dto.getCity());
        existing.setContactPerson(dto.getContactPerson());
        existing.setEmail(dto.getEmail());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setNotes(dto.getNotes());
        existing.setHasFireExtinguishingFacility(dto.isHasFireExtinguishingFacility());
        existing.setFirefightingArea(dto.getFirefightingArea());
        existing.setSufficientClassroomSpace(dto.isSufficientClassroomSpace());
        existing.setLunchProvided(dto.isLunchProvided());
        existing.setTrainerCanJoinLunch(dto.isTrainerCanJoinLunch());
        return locationRepository.save(existing);
    }

    public void deleteLocation(Long id) {
        verifyAdminRole();
        if (!locationRepository.existsById(id)) {
            throw new LocationNotFoundException(id);
        }
        locationRepository.deleteById(id);
    }

    // ============================================================
    // CERTIFICATEN
    // ============================================================

    public List<Certificate> getAllCertificates() {
        verifyAdminRole();
        return certificateRepository.findAll();
    }

    public List<Certificate> getExpiredCertificates() {
        verifyAdminRole();
        LocalDate today = LocalDate.now();
        return certificateRepository.findAll().stream()
                .filter(cert -> cert.getExpiryDate() != null && cert.getExpiryDate().isBefore(today))
                .toList();
    }

    public void deleteCertificate(Long id) {
        verifyAdminRole();
        if (!certificateRepository.existsById(id)) {
            throw new CertificateNotFoundException(id);
        }
        certificateRepository.deleteById(id);
    }

    // ============================================================
    // ONTRUIMINGSVERSLAGEN
    // ============================================================

    public List<EvacuationReport> getAllReports() {
        verifyAdminRole();
        return reportRepository.findAll();
    }

    public EvacuationReport approveReport(Long id) {
        verifyAdminRole();
        EvacuationReport report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));
        report.setStatus(ReportStatus.APPROVED);
        return reportRepository.save(report);
    }

    public EvacuationReport rejectReport(Long id, String remarks) {
        verifyAdminRole();
        EvacuationReport report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));
        report.setStatus(ReportStatus.REJECTED);
        report.setEvaluationAdvice(remarks);
        return reportRepository.save(report);
    }

    // ============================================================
    // BEVEILIGING
    // ============================================================

    private void verifyAdminRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Geen actieve sessie gevonden (JWT ontbreekt of is ongeldig).");
        }
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AccessDeniedException("Alleen beheerders mogen deze actie uitvoeren.");
        }
    }
}
