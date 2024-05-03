package cz.cvut.fel.nss.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        switch (status) {
            case NOT_FOUND:
                return new NotFoundException(status, "User orders are not found");
            case SERVICE_UNAVAILABLE:
                return new ServiceUnavailableException(status, "Order Service is unavailable now, please wait");
            default:
                return new Exception("Unexpected error occurred: " + response.reason());
        }
    }
}
