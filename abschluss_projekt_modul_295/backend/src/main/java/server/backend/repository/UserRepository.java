package backend.repository;

import backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository-Interface für die Verwaltung von User-Objekten.
 * Diese Schnittstelle erweitert JpaRepository und ermöglicht CRUD-Operationen für User-Entitäten.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Überprüft, ob ein Benutzer mit dem angegebenen Benutzernamen existiert.
     *
     * @param username der Benutzername, der überprüft werden soll
     * @return true, wenn ein Benutzer mit dem angegebenen Benutzernamen existiert, andernfalls false
     */
    boolean existsByUsername(String username);
}