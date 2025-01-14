package backend.model;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.validation.constraints.Size;

/**
 * Diese Klasse repräsentiert ein Tastenereignis, das mit einer Sitzung verknüpft ist.
 */
@Entity
public class Keystroke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keystrokeId;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    @Size(max = 2000, message = "Text must be no longer than 2000 characters")
    private String text; 

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String action; 

    @Column(nullable = false)
    private Date sentDate; 

     /**
     * Standardkonstruktor, der das gesendete Datum auf das aktuelle Datum setzt.
     */
    public Keystroke() {
        this.sentDate = new Date(); 
    }

    /**
     * Konstruktor, der eine neue Keystroke-Instanz mit den angegebenen Werten erstellt.
     *
     * @param session die Sitzung, mit der dieses Tastenereignis verknüpft ist
     * @param text der Text, der eingegeben wurde
     * @param action die durchgeführte Aktion
     */
    public Keystroke(Session session, String text, String action) {
        this.session = session;
        this.text = text;
        this.action = action;
        this.sentDate = new Date();
    }

    public void setKeystrokeId(Long keystrokeId) {
        this.keystrokeId = keystrokeId;
    }

    public Long getKeystrokeId() {
        return keystrokeId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Long getSessionId() {
        return session != null ? session.getSessionId() : null;
    }

    @Override
    public String toString() {
        return "Keystroke{" +
                "id=" + keystrokeId +
                ", session=" + (session != null ? session.getSessionId() : "null") +
                ", text='" + text + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
