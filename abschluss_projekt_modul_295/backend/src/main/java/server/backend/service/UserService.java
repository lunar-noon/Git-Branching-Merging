package backend.service;

import backend.model.User;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List; 

/**
 * Service-Klasse für die Verwaltung von User-Objekten.
 * Diese Klasse enthält Geschäftslogik für die Suche nach Benutzern.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Findet einen Benutzer anhand der Benutzer-ID.
     *
     * @param userId die ID des Benutzers, der gesucht werden soll
     * @return ein Optional, das den gefundenen Benutzer enthält, oder leer, wenn kein Benutzer gefunden wurde
     */
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

     /**
     * Ruft alle Benutzer aus der Datenbank ab.
     *
     * @return eine Liste von User-Objekten
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }    

    /**
     * Speichert oder aktualisiert einen Benutzer in der Datenbank.
     *
     * @param user der Benutzer, der gespeichert oder aktualisiert werden soll
     * @return der gespeicherte oder aktualisierte Benutzer
     */
    public User saveUser(User user) {
        return userRepository.save(user); 
    }

    /**
     * Löscht einen Benutzer anhand der Benutzer-ID.
     *
     * @param userId die ID des zu löschenden Benutzers
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
