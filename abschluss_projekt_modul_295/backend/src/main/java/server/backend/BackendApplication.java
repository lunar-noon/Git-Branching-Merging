package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import backend.repository.UserRepository;
import backend.model.User;
import backend.model.UserRole;    

/**
 * Hauptklasse für die Spring Boot-Anwendung.
 * Diese Klasse startet die Anwendung und initialisiert standardmäßige Benutzerdaten.
 */
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     * Wird beim Start der Anwendung aufgerufen.
     * Diese Methode initialisiert einen Standardbenutzer, wenn er nicht bereits existiert.
     *
     * @param args Argumente, die beim Start der Anwendung übergeben werden
     */
    @Override
    public void run(String... args) {
        initializeDefaultUser();
    }

    /**
     * Initialisiert einen Standardbenutzer mit dem Benutzernamen "adminUser",
     * wenn er noch nicht vorhanden ist.
     */
    private void initializeDefaultUser() {
        if (!userRepository.existsByUsername("adminUser")) {
            User adminUser = new User();
            adminUser.setUsername("adminUser");
            adminUser.setRole(UserRole.ADMIN); 
            userRepository.save(adminUser);
            System.out.println("Admin user created with ID: " + adminUser.getUserId());
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
