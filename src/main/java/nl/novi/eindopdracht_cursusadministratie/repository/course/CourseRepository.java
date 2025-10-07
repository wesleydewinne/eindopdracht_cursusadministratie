package nl.novi.eindopdracht_cursusadministratie.repository.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTrainerId(Long trainerId);
}