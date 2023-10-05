package fr.ecolnum.projectapi.exception;

public class NotAuthorizedOperationException extends Exception {
    public NotAuthorizedOperationException(String message) {
        super(message);
    }
}
