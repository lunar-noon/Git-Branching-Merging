package backend.controller;

import backend.model.User;
import backend.repository.UserRepository;
import backend.service.UserService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Der Controller für die Benutzerverwaltung.
 * Dieser Controller ermöglicht die Erstellung neuer Benutzer
 * über RESTful HTTP-Anfragen.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Erstellt einen neuen Benutzer.
     *
     * Diese Methode verarbeitet POST-Anfragen an den 
     * Endpunkt "/createUser". Sie nimmt ein User-Objekt 
     * entgegen, speichert es in der Datenbank und gibt eine 
     * entsprechende Antwort zurück.
     *
     * @param user das zu erstellende User-Objekt
     * @return eine ResponseEntity mit einer Bestätigungsmeldung
     *         und dem neu erstellten Benutzer-ID oder einem Fehlerstatus
     */
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User created with ID: " + user.getUserId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    // Neuimplementierung

    /**
     * Gibt alle Benutzer zurück.
     *
     * Diese Methode verarbeitet GET-Anfragen an den Endpunkt "/api/users".
     * Sie gibt eine Liste aller Benutzer zurück.
     *
     * @return eine Liste aller User-Objekte
     */
    @GetMapping("/api/users") 
    public List<User> getAllUsers() {
        return userService.getAllUsers(); 
    }

    /**
     * Erstellt einen neuen Benutzer.
     *
     * Diese Methode verarbeitet POST-Anfragen an den Endpunkt "/createUser".
     * Sie nimmt ein User-Objekt entgegen, speichert es in der Datenbank und gibt eine
     * entsprechende Antwort zurück.
     *
     * @param user das zu erstellende User-Objekt
     * @return eine ResponseEntity mit einer Bestätigungsmeldung
     *         und der neu erstellten Benutzer-ID oder einem Fehlerstatus
     */
    @PutMapping("/api/users/{userId}") 
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User updatedUser) {
        updatedUser.setUserId(userId);
        User savedUser = userService.saveUser(updatedUser);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Löscht einen Benutzer anhand der Benutzer-ID.
     *
     * Diese Methode verarbeitet DELETE-Anfragen an den Endpunkt "/api/users/{userId}".
     * Sie löscht den Benutzer mit der angegebenen ID und gibt eine leere Antwort zurück.
     *
     * @param userId die ID des zu löschenden Benutzers
     * @return ResponseEntity ohne Inhalt (204 No Content) bei erfolgreichem Löschen
     */
    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
