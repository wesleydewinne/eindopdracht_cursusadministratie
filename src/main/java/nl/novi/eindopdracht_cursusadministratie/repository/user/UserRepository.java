package nl.novi.eindopdracht_cursusadministratie.repository;

import nl.novi.eindopdracht_cursusadministratie.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}