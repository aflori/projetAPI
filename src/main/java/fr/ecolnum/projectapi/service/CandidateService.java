package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for candidate action
 */
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository repository;

    /**
     * this method create a candidate in the database
     *
     * @param candidate candidate to be created in database
     * @return the candidate (with its new ID) created
     */
    public Candidate createCandidate(Candidate candidate) {
        return repository.save(candidate);
    }
}
