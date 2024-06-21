package cz.cvut.fel.nss.exception;

/**
 * Exception thrown when there is an insufficient amount of product available.
 */
public class InsufficientAmountOfProductException extends RuntimeException {
    public InsufficientAmountOfProductException(String message) {
        super(message);
    }
}
