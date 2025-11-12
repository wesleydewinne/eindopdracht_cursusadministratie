package nl.novi.eindopdracht_cursusadministratie.exception;

/**
 * Algemene uitzondering voor het geval een entiteit niet wordt gevonden in de database.
 * Wordt meestal gebruikt door specifieke exceptions zoals CourseNotFoundException of TrainerNotFoundException.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

        public EntityNotFoundException(Class<?> entityClass, Long id) {
            super(entityClass.getSimpleName() + " met id " + id + " niet gevonden.");
        }
    }
