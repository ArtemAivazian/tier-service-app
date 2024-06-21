package cz.cvut.fel.nss.exception;

/**
 * Exception thrown when a Kafka message cannot be constructed.
 */
public class CannotConstructKafkaMessageException extends RuntimeException{

    /**
     * Constructs a new CannotConstructKafkaMessageException with the specified detail message.
     *
     * @param message the detail message
     */
    public CannotConstructKafkaMessageException(String message) {
        super(message);
    }
}
