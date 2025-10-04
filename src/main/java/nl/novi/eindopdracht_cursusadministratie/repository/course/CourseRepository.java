package nl.novi.eindopdracht_cursusadministratie.repository.course;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}