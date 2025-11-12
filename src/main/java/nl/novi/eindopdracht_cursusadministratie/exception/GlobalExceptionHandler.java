package nl.novi.eindopdracht_cursusadministratie.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Globale exception handler voor de hele applicatie.
 * Geeft consistente, nette JSON-responses met Nederlandstalige foutmeldingen.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Algemene fallback voor onverwachte fouten.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Interne serverfout",
                ex.getMessage() != null ? ex.getMessage() : "Er is een onbekende fout opgetreden.",
                request.getRequestURI()
        );
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message;

        if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("users_email_key")) {
            message = "Er bestaat al een gebruiker met dit e-mailadres.";
        }

        else if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("users_pkey")) {
            message = "Er bestaat al een gebruiker met dit ID (primair sleutelconflict).";
        }

        else {
            message = "Ongeldige of dubbele gegevens. Controleer je invoer.";
        }

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Ongeldige aanvraag",
                message,
                request.getRequestURI()
        );
    }


    /**
     * Foutafhandeling voor resources die niet gevonden zijn of algemene runtimefouten.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Resource niet gevonden";

        boolean isNotFound = message.toLowerCase().contains("not found") || message.toLowerCase().contains("niet gevonden");
        HttpStatus status = isNotFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        return buildResponse(status, status == HttpStatus.NOT_FOUND ? "Niet gevonden" : "Ongeldige aanvraag", message, request.getRequestURI());
    }

    /**
     * Foutafhandeling voor beveiligingsproblemen (bijv. onvoldoende rechten).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Toegang geweigerd",
                ex.getMessage() != null ? ex.getMessage() : "Je hebt geen toestemming om deze actie uit te voeren.",
                request.getRequestURI()
        );
    }

    /**
     * Foutafhandeling voor ongeldige invoer (DTO-validatie).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validatiefout");
        body.put("fieldErrors", fieldErrors);
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Hulpmethode om een consistente response op te bouwen.
     */
    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }
}
