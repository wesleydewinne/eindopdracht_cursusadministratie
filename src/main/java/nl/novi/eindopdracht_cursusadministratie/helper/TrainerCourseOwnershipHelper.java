package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.course.Course;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Helperklasse die controleert of de ingelogde trainer of admin
 * eigenaar is van een cursus voordat acties worden uitgevoerd.
 */
public final class TrainerCourseOwnershipHelper {

    private TrainerCourseOwnershipHelper() {
    }

    /**
     * Controleert of de ingelogde gebruiker:
     * - Admin is (mag altijd)
     * - Of de trainer is die gekoppeld is aan de cursus
     *
     * @param course De cursus waarop actie wordt uitgevoerd
     * @throws AccessDeniedException als de gebruiker geen rechten heeft
     */
    public static void verifyOwnershipOrAdmin(Course course) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Geen actieve sessie gevonden (JWT ontbreekt of ongeldig).");
        }

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isTrainer = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRAINER"));

        if (isAdmin) return;

        if (!isTrainer) {
            throw new AccessDeniedException("Alleen trainers of admins mogen deze actie uitvoeren.");
        }

        String loggedInEmail = auth.getName();
        String trainerEmail = course.getTrainer() != null ? course.getTrainer().getEmail() : null;

        if (trainerEmail == null || !trainerEmail.equalsIgnoreCase(loggedInEmail)) {
            throw new AccessDeniedException("Je mag alleen cursussen beheren die aan jou zijn toegewezen.");
        }
    }
}
