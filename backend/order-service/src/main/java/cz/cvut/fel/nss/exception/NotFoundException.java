package cz.cvut.fel.nss.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a requested resource is not found.
 */
public class NotFoundException extends ResponseStatusException {
    public NotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
