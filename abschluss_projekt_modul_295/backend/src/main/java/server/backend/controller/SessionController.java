package backend.controller;

import backend.model.Session;
import backend.model.User;
import backend.service.SessionService;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;


/**
 * Der Controller für die Sitzungsverwaltung.
 * Dieser Controller ermöglicht die Erstellung und den Abruf von Sitzungen
 * über RESTful HTTP-Anfragen.
 */
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserRepository userRepository; 

    /**
     * Erstellt eine neue Sitzung für den angegebenen Benutzer.
     *
     * Diese Methode verarbeitet GET-Anfragen an den 
     * Endpunkt "/createSession". Sie nimmt die Benutzer-ID 
     * als Parameter entgegen, erstellt eine neue Sitzung 
     * und speichert sie. Bei Erfolg wird die Benutzer-ID 
     * zurückgegeben, andernfalls wird ein Fehlerstatus gesendet.
     *
     * @param userId die ID des Benutzers, für den die Sitzung erstellt werden soll
     * @return eine ResponseEntity mit der Benutzer-ID bei Erfolg 
     *         oder einem Fehlerstatus
     */
    @GetMapping("/createSession")
    public ResponseEntity<Object> createSession(@RequestParam Long userId) {
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            Session session = new Session(user);
            sessionService.saveSession(session);

            return ResponseEntity.ok(user.getUserId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating session: " + e.getMessage());
        }
    }

    /**
     * Gibt die Benutzer-ID der aktuellen Sitzung zurück.
     *
     * Diese Methode verarbeitet GET-Anfragen an den Endpunkt "/getSession".
     * Sie verwendet das HttpSession-Objekt, um die Benutzer-ID aus der aktuellen Sitzung abzurufen.
     *
     * @param session die aktuelle HTTP-Sitzung
     * @return die Benutzer-ID der aktuellen Sitzung
     */
    // Interner Endpoint
    @GetMapping("/getSession")
    public Long getSession(HttpSession session) {
        return (Long) session.getAttribute("userId"); 
    }

    // Neuimplementierung

    /**
     * Gibt alle Sitzungen zurück.
     *
     * Diese Methode verarbeitet GET-Anfragen an den Endpunkt "/api/sessions".
     * Sie gibt eine Liste aller Sitzungen zurück.
     *
     * @return eine Liste aller Session-Objekte
     */
    @GetMapping("/api/sessions")
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    /**
     * Löscht eine Sitzung anhand der Sitzungs-ID.
     *
     * Diese Methode verarbeitet DELETE-Anfragen an den Endpunkt "/api/sessions/{sessionId}".
     * Sie löscht die Sitzung mit der angegebenen ID und gibt eine leere Antwort zurück.
     *
     * @param sessionId die ID der zu löschenden Sitzung
     * @return ResponseEntity ohne Inhalt (204 No Content) bei erfolgreichem Löschen
     */
    @DeleteMapping("/api/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }
}
