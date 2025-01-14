package server.backend.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Der globale Ausnahme-Handler für die Anwendung.
 * 
 * Diese Klasse fängt und behandelt Ausnahmen, die während der
 * Verarbeitung von Anfragen in den Controllern auftreten.
 * Insbesondere behandelt sie Validierungsfehler, die bei der
 * Bearbeitung von HTTP-Anfragen mit ungültigen Daten auftreten.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Behandelt Ausnahmen, die bei der Validierung von Methodenargumenten
     * auftreten.
     *
     * @param ex die Ausnahme, die auftritt, wenn die Validierung fehlschlägt
     * @return eine ResponseEntity, die eine Map mit den Fehlern enthält,
     *         sowie den HTTP-Status 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

}
