package nl.novi.eindopdracht_cursusadministratie.controller.location;

import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.repository.location.LocationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return repository.findAll();
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return repository.save(location);
    }
}
