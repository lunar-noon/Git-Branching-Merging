package backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * Diese Klasse repräsentiert einen Benutzer im System.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /* @NotBlank(message = "Username is required")
    @Size(max = 50) */
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* @NotBlank(message = "Role is required") */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * Standardkonstruktor, der das Erstellungsdatum auf die aktuelle Zeit setzt.
     */
    public User() {
        this.createdAt = LocalDateTime.now(); 
    }

    /**
     * Konstruktor, der einen neuen Benutzer mit dem angegebenen Benutzernamen und der Rolle erstellt.
     *
     * @param username der Benutzername des neuen Benutzers
     * @param role die Rolle des neuen Benutzers
     */
    public User(String username, UserRole role) {
        this.username = username;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
