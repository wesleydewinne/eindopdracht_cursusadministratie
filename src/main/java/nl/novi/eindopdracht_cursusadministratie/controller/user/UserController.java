package nl.novi.eindopdracht_cursusadministratie.controller.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import nl.novi.eindopdracht_cursusadministratie.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    //  GET - alle users (optioneel filteren op role)
    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) Role role) {
        if (role != null) {
            return service.getUsersByRole(role);
        }
        return service.getAllUsers();
    }

    //  GET - user op ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //  POST - nieuwe user aanmaken
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = service.createUser(user);
        return ResponseEntity.ok(created);
    }

    //  PUT - user bijwerken
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updated = service.updateUser(id, updatedUser);
        return ResponseEntity.ok(updated);
    }

    //  DELETE - user verwijderen
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
    }
}
