package nl.novi.eindopdracht_cursusadministratie.service.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    //  Alle users ophalen
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    //  User op ID
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    //  User aanmaken
    public User createUser(User user) {
        // TODO: Only allow ADMIN users to create new accounts (add security check later)
        return repository.save(user);
    }

    //  User bijwerken
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

    //  User verwijderen
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    //  Filter op rol
    public List<User> getUsersByRole(Role role) {
        return repository.findByRole(role);
    }
}
