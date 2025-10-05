package nl.novi.eindopdracht_cursusadministratie.service.location;

import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    public Location getLocationById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id));
    }

    public Location createLocation(Location location) {
        if (!location.isHasFireExtinguishingFacility()) {
            location.setFirefightingArea(null); // Leegmaken als er geen voorziening is
        }
        return repository.save(location);
    }

    public Location updateLocation(Long id, Location updated) {
        return repository.findById(id)
                .map(existing -> {
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
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id));
    }

    public void deleteLocation(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
