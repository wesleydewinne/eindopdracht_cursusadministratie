package nl.novi.eindopdracht_cursusadministratie.service.location;

import nl.novi.eindopdracht_cursusadministratie.exception.LocationNotFoundException;
import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.novi.eindopdracht_cursusadministratie.helper.EntityFinderHelper.findEntityById;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    // ============================================================
    // BASIS CRUD
    // ============================================================

    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    public Location getLocationById(Long id) {
        return findEntityById(id, repository, new LocationNotFoundException(id));
    }

    public Location createLocation(Location location) {
        // Als er geen blusvoorziening is, wordt dat veld leeggemaakt
        if (!location.isHasFireExtinguishingFacility()) {
            location.setFirefightingArea(null);
        }
        return repository.save(location);
    }

    public Location updateLocation(Long id, Location updated) {
        Location existing = findEntityById(id, repository, new LocationNotFoundException(id));

        existing.setCompanyName(updated.getCompanyName());
        existing.setAddress(updated.getAddress());
        existing.setPostalCode(updated.getPostalCode());
        existing.setCity(updated.getCity());
        existing.setContactPerson(updated.getContactPerson());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setEmail(updated.getEmail());
        existing.setNotes(updated.getNotes());
        existing.setHasFireExtinguishingFacility(updated.isHasFireExtinguishingFacility());
        existing.setFirefightingArea(updated.isHasFireExtinguishingFacility() ? updated.getFirefightingArea() : null);
        existing.setSufficientClassroomSpace(updated.isSufficientClassroomSpace());
        existing.setLunchProvided(updated.isLunchProvided());
        existing.setTrainerCanJoinLunch(updated.isTrainerCanJoinLunch());

        return repository.save(existing);
    }

    public void deleteLocation(Long id) {
        if (!repository.existsById(id)) {
            throw new LocationNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
