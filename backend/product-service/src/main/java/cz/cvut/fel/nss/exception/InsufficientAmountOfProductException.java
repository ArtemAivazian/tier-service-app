package cz.cvut.fel.nss.exception;

public class InsufficientAmountOfProductException extends RuntimeException {
    public InsufficientAmountOfProductException(String message) {
        super(message);
    }
}
