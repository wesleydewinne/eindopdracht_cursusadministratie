package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.security.CustomUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class TrainerSecurityHelper {

    private TrainerSecurityHelper() {
    }

    /**
     * Controleert of de huidige gebruiker een trainer of admin is.
     */
    public static void verifyTrainerOrAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Geen actieve sessie gevonden (JWT ontbreekt of ongeldig).");
        }

        boolean isAllowed = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TRAINER") || a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAllowed) {
            throw new AccessDeniedException("Alleen trainers of admins mogen deze actie uitvoeren.");
        }
    }

    /**
     * Haalt het ID op van de ingelogde trainer uit het SecurityContext.
     */
    public static Long getAuthenticatedTrainerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Geen actieve sessie gevonden (JWT ontbreekt of ongeldig).");
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }

        throw new AccessDeniedException("Gebruikersinformatie niet beschikbaar of ongeldig.");
    }
}
