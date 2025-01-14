package backend.service;

import backend.model.Session;
import backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List; 


/**
 * Service-Klasse für die Verwaltung von Session-Objekten.
 * Diese Klasse enthält Geschäftslogik für die Speicherung von Sessions.
 */
@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Speichert eine Session in der Datenbank.
     *
     * @param session die zu speichernde Session
     */
    public void saveSession(Session session) {
        sessionRepository.save(session);
    }

    /**
     * Ruft alle Sessions aus der Datenbank ab.
     *
     * @return eine Liste von Session-Objekten
     */
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    /**
     * Löscht eine Session anhand der angegebenen ID.
     *
     * @param sessionId die ID der zu löschenden Session
     */
    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }
}
