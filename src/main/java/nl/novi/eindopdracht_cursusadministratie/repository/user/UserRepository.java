package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // Voor Admin: ophalen van alle users met een specifieke rol
    List<User> findByRole(Role role);

    // Extra query-optie als je ooit per type wil filteren
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(Role role);
}