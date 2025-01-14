package backend.repository;

import backend.model.Keystroke; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository-Interface für die Verwaltung von Keystroke-Objekten.
 * Diese Schnittstelle erweitert JpaRepository und ermöglicht CRUD-Operationen für Keystroke-Entitäten.
 */
@Repository
public interface KeystrokeRepository extends JpaRepository<Keystroke, Long> { 
    /**
     * Findet alle Keystrokes, die mit einer bestimmten Sitzung verknüpft sind.
     *
     * @param sessionId die ID der Sitzung, nach der die Keystrokes gefiltert werden sollen
     * @return eine Liste der Keystrokes, die zu der angegebenen Sitzung gehören
     */
    List<Keystroke> findBySession_SessionId(Long sessionId);
}
