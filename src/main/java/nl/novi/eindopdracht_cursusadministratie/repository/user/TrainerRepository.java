package nl.novi.eindopdracht_cursusadministratie.repository.user;

import nl.novi.eindopdracht_cursusadministratie.model.user.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    // Voor alle zekerheid: expliciet filteren op discriminator of rol
    @Query("SELECT t FROM Trainer t WHERE t.role = 'TRAINER'")
    List<Trainer> findAllTrainers();
}
