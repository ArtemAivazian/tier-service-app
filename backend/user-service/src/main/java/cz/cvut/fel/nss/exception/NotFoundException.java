package cz.cvut.fel.nss.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * NotFoundException is a custom exception that extends ResponseStatusException and represents a 404 Not Found error.
 */
@Getter
public class NotFoundException extends ResponseStatusException {
    /**
     * Constructs a new NotFoundException with the specified status code and reason.
     *
     * @param status the HTTP status code
     * @param reason the reason for the exception
     */
    public NotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
