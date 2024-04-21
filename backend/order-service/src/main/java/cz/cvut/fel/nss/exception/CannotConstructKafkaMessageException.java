package cz.cvut.fel.nss.exception;

public class CannotConstructKafkaMessageException extends RuntimeException{
    public CannotConstructKafkaMessageException(String message) {
        super(message);
    }
}
