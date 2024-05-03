package cz.cvut.fel.nss.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ServiceUnavailableException extends ResponseStatusException {
    public ServiceUnavailableException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}

