package backend.service;

import backend.model.Keystroke; 
import backend.repository.KeystrokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-Klasse für die Verwaltung von Keystroke-Objekten.
 * Diese Klasse enthält Geschäftslogik für die Speicherung und den Abruf von Keystrokes.
 */
@Service
public class KeystrokeService { 

    @Autowired
    private KeystrokeRepository keystrokeRepository;
    /**
     * Speichert einen Keystroke in der Datenbank.
     *
     * @param keystroke der zu speichernde Keystroke
     * @return der gespeicherte Keystroke
     */
    public Keystroke saveKeystroke(Keystroke keystroke) { 
        return keystrokeRepository.save(keystroke); 
    }
    /**
     * Ruft alle Keystrokes aus der Datenbank ab.
     *
     * @return eine Liste von Keystroke-Objekten
     */
    public List<Keystroke> getAllKeystrokes() {
        return keystrokeRepository.findAll();
    }
    

    /**
     * Ruft eine Liste von Keystrokes ab, die mit einer bestimmten Sitzung verknüpft sind.
     *
     * @param sessionId die ID der Sitzung
     * @return eine Liste von Keystroke-Objekten, die zur angegebenen Sitzung gehören
     */
    public List<Keystroke> getKeystrokesBySessionId(Long sessionId) {
        return keystrokeRepository.findBySession_SessionId(sessionId);
    }

    /**
     * Aktualisiert einen vorhandenen Keystroke in der Datenbank.
     *
     * @param updatedKeystroke der Keystroke mit aktualisierten Daten
     * @return der aktualisierte Keystroke
     * @throws RuntimeException wenn der Keystroke mit der angegebenen ID nicht gefunden wird
     */
    public Keystroke updateKeystroke(Keystroke updatedKeystroke) {
        if (!keystrokeRepository.existsById(updatedKeystroke.getKeystrokeId())) {
            throw new RuntimeException("Keystroke not found with id: " + updatedKeystroke.getKeystrokeId());
        }
        return keystrokeRepository.save(updatedKeystroke);
    }

    /**
     * Löscht einen Keystroke anhand der angegebenen ID.
     *
     * @param keystrokeId die ID des zu löschenden Keystrokes
     */
    public void deleteKeystroke(Long keystrokeId) {
        keystrokeRepository.deleteById(keystrokeId);
    }
}
