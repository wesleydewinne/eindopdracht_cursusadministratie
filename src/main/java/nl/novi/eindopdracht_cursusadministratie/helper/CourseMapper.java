package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseAdminResponseDto;
import nl.novi.eindopdracht_cursusadministratie.dto.course.CourseCreateRequestDto;
import nl.novi.eindopdracht_cursusadministratie.dto.registration.RegistrationGroupStudentDto;
import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import nl.novi.eindopdracht_cursusadministratie.model.course.TrainingType;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.model.registration.Registration;
import nl.novi.eindopdracht_cursusadministratie.model.report.EvacuationPhase;
import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;

import java.util.List;

public class CourseMapper {

    private CourseMapper() {
    }


    // ============================================================
    // ADMIN RESPONSE DTO
    // ============================================================
    public static CourseAdminResponseDto toAdminDto(Course course) {

        String address = null;
        String postalCode = null;
        String city = null;

        if (course.getLocation() != null) {
            address = course.getLocation().getAddress();
            postalCode = course.getLocation().getPostalCode();
            city = course.getLocation().getCity();
        }


        String trainerName = course.getTrainer() != null ? course.getTrainer().getName() : null;

        List<RegistrationGroupStudentDto> studentDtos = course.getRegistrations().stream()
                .map(r -> new RegistrationGroupStudentDto(
                        r.getStudent().getId(),
                        r.getStudent().getName()
                ))
                .toList();

        return new CourseAdminResponseDto(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getStartDate(),
                course.getEndDate(),
                course.getMaxParticipants(),
                course.isAdminOverrideAllowed(),
                course.getType().name(),
                address,
                postalCode,
                city,
                trainerName,
                course.isReportRequired(),
                course.isEvacuationTraining(),
                studentDtos
        );
    }


    // ============================================================
    // VAN CREATE DTO → ENTITY
    // ============================================================

    public static Course fromCreateDto(CourseCreateRequestDto dto, Location location, Trainer trainer) {
        Course course = new Course();

        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setMaxParticipants(dto.getMaxParticipants());
        course.setAdminOverrideAllowed(dto.isAdminOverrideAllowed());
        course.setType(TrainingType.valueOf(dto.getType().toUpperCase()));
        course.setLocation(location);
        course.setTrainer(trainer);
        course.setReportRequired(dto.isReportRequired());

        if (dto.getPhase() != null) {
            try {
                course.setPhase(EvacuationPhase.valueOf(dto.getPhase().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Ongeldige fase: " + dto.getPhase());
            }
        }

        return course;
    }
}
