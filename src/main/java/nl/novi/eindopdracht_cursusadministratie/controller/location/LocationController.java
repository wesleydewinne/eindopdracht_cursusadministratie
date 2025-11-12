package nl.novi.eindopdracht_cursusadministratie.controller.location;

import nl.novi.eindopdracht_cursusadministratie.model.location.Location;
import nl.novi.eindopdracht_cursusadministratie.service.location.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(service.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLocationById(id));
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody Location location) {
        Location saved = service.createLocation(location);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable Long id, @RequestBody Location updated) {
        return ResponseEntity.ok(service.updateLocation(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteLocation(id);
        return ResponseEntity.ok("Location with ID " + id + " has been deleted successfully.");
    }
}
