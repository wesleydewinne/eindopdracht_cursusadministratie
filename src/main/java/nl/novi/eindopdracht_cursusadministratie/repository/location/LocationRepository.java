package nl.novi.eindopdracht_cursusadministratie.repository.location;


import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
