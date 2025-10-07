package nl.novi.eindopdracht_cursusadministratie.helper;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import java.util.HashMap;
import java.util.Map;

/**
 * De {@code RoleIdHelper} is verantwoordelijk voor het genereren van unieke,
 * oplopende identificatienummers binnen elke gebruikersrol (bijv. ADMIN, TRAINER, STUDENT).
 * <p>
 * Dit zorgt ervoor dat iedere rol zijn eigen teller heeft, zodat bijvoorbeeld
 * de eerste trainer en de eerste cursist allebei ID 1 kunnen hebben binnen hun rol,
 * zonder dat dit conflicteert in de database.
 * <p>
 * Wordt gebruikt in {@code UserService} bij het aanmaken van nieuwe gebruikers.
 */
public class RoleIdHelper {

    /**
     * Houdt de actuele teller per gebruikersrol bij.
     * De sleutel is de {@link Role}, en de waarde is het laatst uitgegeven ID voor die rol.
     */
    private static final Map<Role, Integer> counters = new HashMap<>();

    /**
     * Genereert een nieuw oplopend rol-specifiek ID voor de opgegeven gebruikersrol.
     * <p>
     * Elke rol begint bij 1 en wordt onafhankelijk van andere rollen opgehoogd.
     * Bijvoorbeeld:
     * <ul>
     *     <li>Admin 1, Admin 2, ...</li>
     *     <li>Trainer 1, Trainer 2, ...</li>
     *     <li>Cursist 1, Cursist 2, ...</li>
     * </ul>
     *
     * @param role de gebruikersrol waarvoor een nieuw ID wordt gegenereerd
     * @return het volgende rol-specifieke ID (bijv. 1, 2, 3, ...)
     */
    public static int generateRoleSpecificId(Role role) {
        counters.putIfAbsent(role, 0);
        int nextId = counters.get(role) + 1;
        counters.put(role, nextId);
        return nextId;
    }
}
