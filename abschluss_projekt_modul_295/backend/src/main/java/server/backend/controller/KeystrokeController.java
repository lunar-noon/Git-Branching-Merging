package backend.controller;

import backend.model.Keystroke;
import backend.model.Session;
import backend.repository.KeystrokeRepository;
import backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Der Controller für die Verarbeitung von Keystroke-Nachrichten über STOMP.
 * Dieser Controller empfängt Keystroke-Objekte über STOMP-WebSocket-Nachrichten
 * und speichert sie in der Datenbank.
 */
@Controller
public class KeystrokeController {


    @Autowired
    private KeystrokeRepository keystrokeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Verarbeitet eingehende Keystroke-Nachrichten.
     *
     * Diese Methode wird aufgerufen, wenn eine Nachricht an den 
     * Endpunkt "/keystroke" gesendet wird. Sie speichert das 
     * empfangene Keystroke-Objekt in der Datenbank und sendet 
     * es an alle Abonnenten des Topics "/topic/keystrokes".
     *
     * @param keystroke das empfangene Keystroke-Objekt
     * @return das neu gespeicherte Keystroke-Objekt
     * @throws RuntimeException wenn das Keystroke-Objekt oder 
     *                          die zugehörige Session-Information 
     *                          fehlt oder die Session nicht gefunden wird
     */
    @MessageMapping("/keystroke")
    @SendTo("/topic/keystrokes")
    public Keystroke handleKeystroke(Keystroke keystroke) {

        System.out.println("Received keystroke: " + keystroke);

        if (keystroke == null || keystroke.getSession() == null || 
            keystroke.getSession().getSessionId() == null) {
            throw new RuntimeException("Keystroke or its session information is missing");
        }

        Session session = sessionRepository.findById(keystroke.getSession().getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));


        Keystroke newKeystroke = new Keystroke(session, keystroke.getText(), keystroke.getAction());
        keystrokeRepository.save(newKeystroke);

        return newKeystroke; 
    }

}
