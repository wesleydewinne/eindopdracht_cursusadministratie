package nl.novi.eindopdracht_cursusadministratie.repository.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository voor cursussen.
 * Bevat methoden om cursussen op te halen op basis van trainer of andere relaties.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Haalt alle cursussen op die door een specifieke trainer worden gegeven.
     */
    List<Course> findByTrainer_Id(Long trainerId);

    /**
     * Controleert of er een cursus bestaat op een bepaalde locatie
     * waarbij een specifieke cursist ingeschreven is.
     * (Gebruikt voor rapporttoegang.)
     */
    boolean existsByLocation_IdAndRegistrations_Student_Id(Long locationId, Long studentId);
}
