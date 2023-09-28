package fr.ecolnum.projectapi.exception;

public class MultipartFileIsNotImageException extends Exception {

    public MultipartFileIsNotImageException() {
    }

    public MultipartFileIsNotImageException(String message) {
        super(message);
    }
}
