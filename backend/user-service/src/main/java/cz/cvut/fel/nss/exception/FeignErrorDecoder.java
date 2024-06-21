package cz.cvut.fel.nss.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * FeignErrorDecoder is a custom error decoder for Feign clients, translating HTTP status codes into exceptions.
 */
public class FeignErrorDecoder implements ErrorDecoder {

    /**
     * Decodes an HTTP response into a corresponding exception.
     *
     * @param methodKey the method key
     * @param response  the response from the HTTP request
     * @return an exception based on the HTTP status code
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        return switch (status) {
            case NOT_FOUND -> new NotFoundException(status, "User orders are not found");
            case SERVICE_UNAVAILABLE ->
                    new ServiceUnavailableException(status, "Order Service is unavailable now, please wait");
            default -> new Exception("Unexpected error occurred: " + response.reason());
        };
    }
}
