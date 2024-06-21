package cz.cvut.fel.nss.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * ServiceUnavailableException is a custom exception that extends ResponseStatusException and represents a 503 Service Unavailable error.
 */
@Getter
public class ServiceUnavailableException extends ResponseStatusException {
    /**
     * Constructs a new ServiceUnavailableException with the specified status code and reason.
     *
     * @param status the HTTP status code
     * @param reason the reason for the exception
     */
    public ServiceUnavailableException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

