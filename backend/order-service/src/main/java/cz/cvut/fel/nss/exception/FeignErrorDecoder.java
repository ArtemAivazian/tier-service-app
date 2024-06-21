package cz.cvut.fel.nss.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * Custom error decoder for Feign clients.
 */
public class FeignErrorDecoder implements ErrorDecoder {

    /**
     * Decodes the error response from a Feign client.
     *
     * @param methodKey the method key
     * @param response  the response
     * @return an exception based on the response status
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());
        switch (status) {
            case INTERNAL_SERVER_ERROR -> {
                if (methodKey.contains("getProduct"))
                    return new ProductNotFoundException("Product is not found.");
            }
            case NOT_FOUND -> {
                return new NotFoundException(status, "Uri to Product Service endpoint getProductByName is incorrect");
            }
            case SERVICE_UNAVAILABLE -> {
                return new ServiceUnavailableException(status, "Order Service is unavailable now, please wait");
            }
            default -> {
                return new Exception("Unexpected error occurred: " + response.reason());
            }
        }
        return null;
    }
}
