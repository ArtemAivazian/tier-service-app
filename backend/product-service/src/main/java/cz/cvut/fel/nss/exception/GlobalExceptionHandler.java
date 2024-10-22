package cz.cvut.fel.nss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles StockNotFoundException.
     *
     * @param ex the exception
     * @return a response entity with error details
     */
    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<Object> handleStockNotFoundException(StockNotFoundException ex) {
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", ex.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ProductNotFoundException.
     *
     * @param ex the exception
     * @return a response entity with error details
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", ex.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InsufficientAmountOfProductException.
     *
     * @param ex the exception
     * @return a response entity with error details
     */
    @ExceptionHandler(InsufficientAmountOfProductException.class)
    public ResponseEntity<Object> handleInsufficientAmountOfProductException(InsufficientAmountOfProductException ex) {
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "message", ex.getMessage()
        );
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}