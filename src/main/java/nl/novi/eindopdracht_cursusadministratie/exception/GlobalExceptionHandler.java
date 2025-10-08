package nl.novi.eindopdracht_cursusadministratie.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Globale exception handler voor de hele applicatie.
 * Vangt runtime-fouten en validatieproblemen op en geeft nette JSON-responses terug.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Algemene fallback voor onverwachte fouten.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage(), request.getRequestURI());
  }

  /**
   * Foutafhandeling voor resources die niet gevonden zijn (bijv. cursus, gebruiker, certificaat).
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
    String message = ex.getMessage() != null ? ex.getMessage() : "Resource not found";
    HttpStatus status = message.toLowerCase().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
    return buildResponse(status, status.getReasonPhrase(), message, request.getRequestURI());
  }

  /**
   * Foutafhandeling voor ongeldige invoer (bijv. DTO-validatie).
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Validation failed");
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
