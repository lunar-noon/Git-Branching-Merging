package backend.controller;

import backend.model.Keystroke;
import backend.service.KeystrokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/keystrokes")
public class KeystrokeControllerRest {
    
    @Autowired
    private KeystrokeService keystrokeService;

    /**
     * Endpoint zum Abrufen aller Keystrokes.
     * 
     * @return Eine Liste aller Keystroke-Objekte.
     */
    @GetMapping
    public List<Keystroke> getAllKeystrokes() {
        return keystrokeService.getAllKeystrokes();
    }

     /**
     * Endpoint zum Aktualisieren eines Keystrokes.
     *
     * @param keystrokeId          Die ID des zu aktualisierenden Keystrokes.
     * @param updatedKeystroke     Das aktualisierte Keystroke-Objekt.
     * @return ResponseEntity mit dem aktualisierten Keystroke-Objekt.
     */
     @PutMapping("/{keystrokeId}")
     public ResponseEntity<Keystroke> updateKeystroke(@PathVariable Long keystrokeId, @RequestBody Keystroke updatedKeystroke) {
        updatedKeystroke.setKeystrokeId(keystrokeId); 
        Keystroke keystroke = keystrokeService.updateKeystroke(updatedKeystroke);
        return ResponseEntity.ok(keystroke);
     }

     /**
     * Endpoint zum Abrufen der Keystrokes basierend auf einer Sitzungs-ID.
     * 
     * @param sessionId Die ID der Sitzung, für die die Keystrokes abgerufen werden sollen.
     * @return Eine Liste der Keystrokes für die angegebene Sitzungs-ID.
     */
    @GetMapping("/session/{sessionId}")
    public List<Keystroke> getKeystrokesBySessionId(@PathVariable Long sessionId) {
        return keystrokeService.getKeystrokesBySessionId(sessionId);
    }

    /**
     * Endpoint zum Löschen eines Keystrokes.
     *
     * @param keystrokeId Die ID des zu löschenden Keystrokes.
     * @return ResponseEntity ohne Inhalt (204 No Content) bei erfolgreichem Löschen.
     */
    @DeleteMapping("/{keystrokeId}")
    public ResponseEntity<Void> deleteKeystroke(@PathVariable Long keystrokeId) {
        keystrokeService.deleteKeystroke(keystrokeId);
        return ResponseEntity.noContent().build();
    }
}
