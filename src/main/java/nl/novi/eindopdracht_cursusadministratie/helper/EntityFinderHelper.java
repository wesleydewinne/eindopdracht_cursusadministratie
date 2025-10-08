package nl.novi.eindopdracht_cursusadministratie.helper;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Algemene helperklasse om entiteiten op te halen via hun ID.
 * Gooit een opgegeven RuntimeException als de entiteit niet bestaat.
 * <p>
 * Deze klasse kan in alle services worden gebruikt.
 */
public class EntityFinderHelper {

    /**
     * Haalt een entiteit op of gooit een custom exception als deze niet bestaat.
     *
     * @param id         ID van de entiteit
     * @param repository De JPA repository die wordt gebruikt
     * @param exception  De exception die gegooid moet worden
     * @return De gevonden entiteit
     * @param <T> Type van de entiteit
     */
    public static <T> T findEntityById(Long id, JpaRepository<T, Long> repository, RuntimeException exception) {
        return repository.findById(id).orElseThrow(() -> exception);
    }
}
