package nl.novi.eindopdracht_cursusadministratie.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.course.*;
import nl.novi.eindopdracht_cursusadministratie.dto.response.DeleteResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.user.*;
import nl.novi.eindopdracht_cursusadministratie.dto.certificate.CertificateResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.CreateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.UpdateLocationDto;
import nl.novi.eindopdracht_cursusadministratie.helper.CourseMapper;
import nl.novi.eindopdracht_cursusadministratie.helper.LocationMapperHelper;
import nl.novi.eindopdracht_cursusadministratie.helper.UserMapperHelper;
import nl.novi.eindopdracht_cursusadministratie.model.certificate.Certificate;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationReport;
import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import nl.novi.eindopdracht_cursusadministratie.service.location.LocationService;
import nl.novi.eindopdracht_cursusadministratie.service.user.AdminService;
import nl.novi.eindopdracht_cursusadministratie.service.user.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller voor alle adminfunctionaliteiten:
 * - Gebruikersbeheer (cursisten, trainers, admins)
 * - Cursusbeheer
 * - Locatiebeheer
 * - Certificaatbeheer
 * - Rapportbeheer (ontruimingsverslagen)
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;
    private final CourseService courseService;
    private final TrainerService trainerService;
    private final LocationService locationService;


    // ============================================================
    // GEBRUIKERS
    // ============================================================

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponseDto>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseEntity.ok(
                users.stream()
                        .map(UserMapperHelper::toAdminDto)
                        .toList()
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<DeleteResponseDto> deleteUser(@PathVariable Long id) {
        String message = adminService.deleteUser(id);
        return ResponseEntity.ok(new DeleteResponseDto(message));
    }


    // ============================================================
    // CURSISTEN
    // ============================================================

    @GetMapping("/cursisten")
    public ResponseEntity<List<CursistResponseDto>> getAllCursisten() {
        return ResponseEntity.ok(
                adminService.getAllCursisten().stream()
                        .map(UserMapperHelper::toCursistDto)
                        .toList()
        );
    }

    @PostMapping("/cursisten")
    public ResponseEntity<CursistResponseDto> createCursist(@Valid @RequestBody AdminCursistCreateDto dto) {
        Cursist created = adminService.createCursistFromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapperHelper.toCursistDto(created));
    }

    @PutMapping("/cursisten/{id}")
    public ResponseEntity<CursistResponseDto> updateCursist(
            @PathVariable Long id,
            @Valid @RequestBody AdminCursistCreateDto dto
    ) {
        Cursist updated = adminService.updateCursistFromDto(id, dto);
        return ResponseEntity.ok(UserMapperHelper.toCursistDto(updated));
    }


    // ============================================================
    // TRAINERS
    // ============================================================

    @GetMapping("/trainers")
    public ResponseEntity<List<TrainerResponseDto>> getAllTrainers() {
        return ResponseEntity.ok(
                adminService.getAllTrainers().stream()
                        .map(UserMapperHelper::toTrainerDto)
                        .toList()
        );
    }

    @PostMapping("/trainers")
    public ResponseEntity<TrainerResponseDto> createTrainer(@Valid @RequestBody AdminTrainerCreateDto dto) {
        Trainer created = adminService.createTrainerFromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapperHelper.toTrainerDto(created));
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<TrainerResponseDto> updateTrainer(
            @PathVariable Long id,
            @Valid @RequestBody TrainerUpdateRequestDto dto
    ) {
        Trainer updated = adminService.updateTrainer(id, dto);
        return ResponseEntity.ok(UserMapperHelper.toTrainerDto(updated));
    }


    // ============================================================
    // CURSUSSEN
    // ============================================================

    @GetMapping("/courses")
    public ResponseEntity<List<CourseAdminResponseDto>> getAllCourses() {
        List<Course> courses = adminService.getAllCourses();
        return ResponseEntity.ok(
                courses.stream()
                        .map(CourseMapper::toAdminDto)
                        .toList()
        );
    }

    @GetMapping("/trainers/{trainerId}/courses")
    public ResponseEntity<List<CourseTrainerResponseDto>> getCoursesByTrainerAsAdmin(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainerService.getCoursesByTrainer(trainerId));
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseAdminResponseDto> createCourse(
            @Valid @RequestBody CourseCreateRequestDto dto
    ) {
        Course created = adminService.createCourseFromDto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CourseMapper.toAdminDto(created));
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseAdminResponseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseUpdateRequestDto dto
    ) {
        Course updated = adminService.updateCourseFromDto(id, dto);
        return ResponseEntity.ok(CourseMapper.toAdminDto(updated));
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<DeleteResponseDto> deleteCourse(@PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.ok(new DeleteResponseDto("📚 Cursus met ID " + id + " is succesvol verwijderd."));
    }


    // ============================================================
    // REGISTRATIES
    // ============================================================

    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<DeleteResponseDto> deleteRegistration(@PathVariable Long id) {
        adminService.deleteRegistration(id);
        return ResponseEntity.ok(new DeleteResponseDto("📝 Registratie met ID " + id + " is succesvol verwijderd."));
    }


    // ============================================================
    //  LOCATIES
    // ============================================================

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@Valid @RequestBody CreateLocationDto dto) {
        Location entity = LocationMapperHelper.toEntity(dto);
        Location created = locationService.createLocation(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Location> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLocationDto dto
    ) {
        Location updatedEntity = LocationMapperHelper.toEntity(dto);
        Location updated = locationService.updateLocation(id, updatedEntity);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<DeleteResponseDto> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok(new DeleteResponseDto("Locatie met ID " + id + " is succesvol verwijderd."));
    }


    // ============================================================
    // CERTIFICATEN
    // ============================================================

    @GetMapping("/certificates")
    public ResponseEntity<List<CertificateResponseDto>> getAllCertificates() {
        return ResponseEntity.ok(
                adminService.getAllCertificates().stream()
                        .map(cert -> new CertificateResponseDto(
                                cert.getId(),
                                cert.getCertificateNumber(),
                                cert.getCourse().getName(),
                                cert.getStudent().getName(),
                                cert.getIssueDate(),
                                cert.getExpiryDate(),
                                cert.getIssuedBy()
                        ))
                        .toList()
        );
    }

    @GetMapping("/certificates/expired")
    public ResponseEntity<List<CertificateResponseDto>> getExpiredCertificates() {
        return ResponseEntity.ok(
                adminService.getExpiredCertificates().stream()
                        .map(cert -> new CertificateResponseDto(
                                cert.getId(),
                                cert.getCertificateNumber(),
                                cert.getCourse().getName(),
                                cert.getStudent().getName(),
                                cert.getIssueDate(),
                                cert.getExpiryDate(),
                                cert.getIssuedBy()
                        ))
                        .toList()
        );
    }

    @DeleteMapping("/certificates/{id}")
    public ResponseEntity<DeleteResponseDto> deleteCertificate(@PathVariable Long id) {
        adminService.deleteCertificate(id);
        return ResponseEntity.ok(new DeleteResponseDto("🪪 Certificaat met ID " + id + " is succesvol verwijderd."));
    }


    // ============================================================
    // ONTRUIMINGSVERSLAGEN
    // ============================================================

    @GetMapping("/reports")
    public ResponseEntity<List<EvacuationReport>> getAllReports() {
        return ResponseEntity.ok(adminService.getAllReports());
    }

    @PutMapping("/reports/{id}/approve")
    public ResponseEntity<EvacuationReport> approveReport(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveReport(id));
    }

    @PutMapping("/reports/{id}/reject")
    public ResponseEntity<EvacuationReport> rejectReport(
            @PathVariable Long id,
            @RequestParam String remarks
    ) {
        return ResponseEntity.ok(adminService.rejectReport(id, remarks));
    }
}
