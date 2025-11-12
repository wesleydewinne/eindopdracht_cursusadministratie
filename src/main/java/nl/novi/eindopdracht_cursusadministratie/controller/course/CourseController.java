package nl.novi.eindopdracht_cursusadministratie.controller.course;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nl.novi.eindopdracht_cursusadministratie.dto.course.*;
import nl.novi.eindopdracht_cursusadministratie.dto.course.TrainerCourseSummaryDto;
import nl.novi.eindopdracht_cursusadministratie.dto.location.*;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.*;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationGroupStudentDto;
import nl.novi.eindopdracht_cursusadministratie.dto.response.DeleteResponseDto;
import nl.novi.eindopdracht_cursusadministratie.exception.UserNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.helper.CourseMapper;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import nl.novi.eindopdracht_cursusadministratie.service.course.CourseService;
import nl.novi.eindopdracht_cursusadministratie.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;
    private final AdminService adminService;


    // ============================================================
    // ADMIN ENDPOINTS
    // ============================================================

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CourseAdminResponseDto>> getAllCourses() {
        List<CourseAdminResponseDto> dtos = courseService.getAllCourses().stream()
                .map(this::toAdminDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseAdminResponseDto> createCourse(@RequestBody Course course) {
        Course created = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAdminDto(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseAdminResponseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseUpdateRequestDto dto
    ) {
        Course updated = adminService.updateCourseFromDto(id, dto);
        return ResponseEntity.ok(CourseMapper.toAdminDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        String message = "Cursus met ID " + id + " is succesvol verwijderd.";
        return ResponseEntity.ok(new DeleteResponseDto(message));
    }


    // ============================================================
    // TRAINER ENDPOINTS
    // ============================================================

    /**
     * 1. Overzicht van alle cursussen van de ingelogde trainer (samenvatting)
     */
    @GetMapping("/trainer")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<TrainerCourseSummaryDto>> getCoursesByTrainer(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Trainer niet gevonden."));

        List<TrainerCourseSummaryDto> dtos = courseService.getCoursesByTrainer(trainer.getId()).stream()
                .map(c -> new TrainerCourseSummaryDto(
                        c.getId(),
                        c.getName(),
                        c.getStartDate(),
                        c.getEndDate(),
                        c.getRegistrations() != null ? c.getRegistrations().size() : 0,
                        c.getLocation() != null
                                ? new LocationTrainerCompactDto(
                                c.getLocation().getId(),
                                c.getLocation().getCompanyName(),
                                c.getLocation().getAddress(),
                                c.getLocation().getPostalCode(),
                                c.getLocation().getCity()
                        )
                                : null
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * 2. Detailoverzicht van één cursus met namen van cursisten
     */
    @GetMapping("/{courseId}/trainer")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<CourseTrainerResponseDto> getCourseDetailsForTrainer(
            @PathVariable Long courseId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        User trainer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Trainer niet gevonden."));

        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Warning", "Cursus niet gevonden.")
                    .build();
        }

        if (course.getTrainer() == null || !course.getTrainer().getId().equals(trainer.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("Warning", "Je kunt alleen je eigen cursussen bekijken.")
                    .build();
        }

        List<RegistrationTrainerResponseDto> registrations = course.getRegistrations().stream()
                .map(r -> new RegistrationTrainerResponseDto(
                        r.getId(),
                        r.getStudent().getId(),
                        r.getStudent().getName(),
                        r.isPresent(),
                        r.getStatus()
                ))
                .toList();

        CourseTrainerResponseDto dto = new CourseTrainerResponseDto(
                course.getId(),
                course.getName(),
                course.getStartDate(),
                course.getEndDate(),
                course.getRegistrations().size(),
                course.getLocation() != null
                        ? new LocationTrainerResponseDto(
                        course.getLocation().getId(),
                        course.getLocation().getCompanyName(),
                        course.getLocation().getAddress(),
                        course.getLocation().getPostalCode(),
                        course.getLocation().getCity()
                )
                        : null,
                registrations
        );

        return ResponseEntity.ok(dto);
    }


    // ============================================================
    // CURSIST ENDPOINTS
    // ============================================================

    @GetMapping("/public")
    @PreAuthorize("hasAnyRole('CURSIST','ADMIN')")
    public ResponseEntity<List<CourseStudentResponseDto>> getAvailableCoursesForStudents() {
        List<CourseStudentResponseDto> dtos = courseService.getAvailableCoursesForStudents().stream()
                .map(this::toStudentDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','CURSIST')")
    public ResponseEntity<CourseAdminResponseDto> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(toAdminDto(course));
    }


    // ============================================================
    // DTO MAPPERS
    // ============================================================

    private CourseAdminResponseDto toAdminDto(@NotNull Course c) {
        return new CourseAdminResponseDto(
                c.getId(),
                c.getName(),
                c.getDescription(),
                c.getStartDate(),
                c.getEndDate(),
                c.getMaxParticipants() != null ? c.getMaxParticipants() : 0,
                c.isAdminOverrideAllowed(),
                c.getType().name(),
                c.getLocation() != null ? c.getLocation().getAddress() : null,
                c.getLocation() != null ? c.getLocation().getPostalCode() : null,
                c.getLocation() != null ? c.getLocation().getCity() : null,
                c.getTrainer() != null ? c.getTrainer().getName() : null,
                c.isReportRequired(),
                c.isEvacuationTraining(),
                c.getRegistrations().stream()
                        .map(r -> new RegistrationGroupStudentDto(
                                r.getStudent().getId(),
                                r.getStudent().getName()
                        ))
                        .toList()
        );
    }

    private CourseStudentResponseDto toStudentDto(Course c) {
        return new CourseStudentResponseDto(
                c.getId(),
                c.getName(),
                c.getStartDate(),
                c.getEndDate(),
                c.getLocation() != null
                        ? new LocationStudentResponseDto(
                        c.getLocation().getId(),
                        c.getLocation().getCompanyName()
                )
                        : null
        );
    }
}
