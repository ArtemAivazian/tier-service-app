package cz.cvut.fel.nss.exception;

/**
 * Exception thrown when a stock is not found.
 */
public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
        super(message);
    }
}

