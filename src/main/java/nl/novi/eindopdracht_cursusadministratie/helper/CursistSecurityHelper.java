package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.user.Cursist;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Helperklasse voor toegangscontrole van cursisten.
 * Voorkomt dubbele code in controllers.
 */
public final class CursistSecurityHelper {

    private CursistSecurityHelper() {
    }

    /**
     * Controleert of de huidige gebruiker toegang heeft
     * tot de gegevens van de opgegeven cursist.
     *
     * @param userDetails De ingelogde gebruiker (JWT)
     * @param cursist De cursist waarvan gegevens worden opgevraagd
     * @return true als gebruiker admin is of dezelfde cursist
     */
    public static boolean hasAccess(UserDetails userDetails, Cursist cursist) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        boolean isEigenCursist = userDetails.getUsername().equalsIgnoreCase(cursist.getEmail());

        return isAdmin || isEigenCursist;
    }
}
