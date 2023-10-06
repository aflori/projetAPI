package fr.ecolnum.projectapi.exception;

public class MultipartFileIsNotAnArchiveException extends Exception {

    public MultipartFileIsNotAnArchiveException() {
        super();
    }

    public MultipartFileIsNotAnArchiveException(String message) {
        super(message);
    }
}