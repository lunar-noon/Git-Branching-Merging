package backend.repository;

import backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository-Interface für die Verwaltung von Session-Objekten.
 * Diese Schnittstelle erweitert JpaRepository und ermöglicht CRUD-Operationen für Session-Entitäten.
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
     /**
     * Findet eine Session anhand ihrer Session-ID.
     *
     * @param sessionId die ID der gesuchten Session
     * @return die gefundene Session oder null, wenn keine Session mit dieser ID existiert
     */
    Session findBySessionId(Long sessionId);
}
