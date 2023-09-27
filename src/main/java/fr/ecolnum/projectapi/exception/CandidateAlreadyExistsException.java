package fr.ecolnum.projectapi.exception;

import fr.ecolnum.projectapi.model.Candidate;

import java.util.Set;

public class CandidateAlreadyExistsException extends Exception {
    public CandidateAlreadyExistsException(String message) {
        super(message);
    }
}
