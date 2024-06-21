package cz.cvut.fel.nss.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a service is unavailable.
 */
public class ServiceUnavailableException extends ResponseStatusException {
    public ServiceUnavailableException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

