package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Role;
import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
}