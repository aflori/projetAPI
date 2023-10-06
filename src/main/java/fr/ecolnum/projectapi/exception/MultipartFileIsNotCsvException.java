package fr.ecolnum.projectapi.exception;

public class MultipartFileIsNotCsvException extends Exception {

    public MultipartFileIsNotCsvException() {
        super();
    }

    public MultipartFileIsNotCsvException(String message) {
        super(message);
    }
}