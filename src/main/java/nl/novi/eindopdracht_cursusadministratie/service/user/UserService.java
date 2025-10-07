package nl.novi.eindopdracht_cursusadministratie.service.user;

import nl.novi.eindopdracht_cursusadministratie.helper.RoleIdHelper;
import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * De {@code UserService} beheert alle CRUD-operaties voor gebruikers
 * (Admins, Trainers en Cursisten).
 * <p>
 * Daarnaast zorgt deze service ervoor dat elke gebruiker een uniek,
 * rol-specifiek ID krijgt via de {@link RoleIdHelper}.
 */
@Service
public class UserService {

    private final UserRepository repository;

    /**
     * Constructor injecteert de {@link UserRepository} voor databasebewerkingen.
     *
     * @param repository de repository die toegang biedt tot gebruikersdata
     */
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Haalt alle gebruikers op.
     *
     * @return lijst met alle gebruikers
     */
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    /**
     * Haalt een gebruiker op via zijn unieke ID.
     *
     * @param id het unieke ID van de gebruiker
     * @return de gevonden {@link User}
     * @throws IllegalArgumentException als de gebruiker niet bestaat
     */
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * Maakt een nieuwe gebruiker aan in de database.
     * <p>
     * Bij het aanmaken krijgt de gebruiker automatisch een rol-specifiek ID via
     * de {@link RoleIdHelper}.
     * <p>
     * TODO: alleen ADMIN-gebruikers mogen accounts aanmaken (wordt later toegevoegd).
     *
     * @param user de gebruiker die moet worden aangemaakt
     * @return de opgeslagen {@link User}
     */
    public User createUser(User user) {
        if (user.getRole() != null && user.getRoleSpecificId() == null) {
            user.setRoleSpecificId(RoleIdHelper.generateRoleSpecificId(user.getRole()));
        }

        // TODO: Only allow ADMIN users to create new accounts (add security check later)
        return repository.save(user);
    }

    /**
     * Werk de gegevens van een bestaande gebruiker bij.
     *
     * @param id          het ID van de gebruiker die moet worden bijgewerkt
     * @param updatedUser de bijgewerkte gebruikersinformatie
     * @return de aangepaste {@link User}
     * @throws IllegalArgumentException als de gebruiker niet wordt gevonden
     */
    public User updateUser(Long id, User updatedUser) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedUser.getName());
                    existing.setEmail(updatedUser.getEmail());
                    existing.setRole(updatedUser.getRole());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * Verwijdert een gebruiker uit de database.
     *
     * @param id het ID van de gebruiker die moet worden verwijderd
     */
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    /**
     * Haalt alle gebruikers op van een specifieke rol.
     *
     * @param role de gebruikersrol (ADMIN, TRAINER, STUDENT)
     * @return lijst met gebruikers die deze rol hebben
     */
    public List<User> getUsersByRole(Role role) {
        return repository.findByRole(role);
    }
}
