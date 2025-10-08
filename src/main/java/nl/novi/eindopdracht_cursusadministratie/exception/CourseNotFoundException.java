package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Exception die wordt gegooid wanneer een cursus niet wordt gevonden.
 */
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
