package nl.novi.eindopdracht_cursusadministratie.repository;

import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findByExpertiseContainingIgnoreCase(String expertise);
}
