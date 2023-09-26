package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

    public Candidate checkDuplicate(Candidate candidate) {

        boolean isDuplicate = false;

        String lastName = candidate.getLastName();
        String firstName = candidate.getFirstName();
        Iterable<Candidate> candidateList = repository.findAll();

        Iterator<Candidate> iter;
        iter = candidateList.iterator();

        while (iter.hasNext()) {
            Candidate candidateDB = iter.next();
            String lastNameDB = candidateDB.getLastName();
            if (lastName.equalsIgnoreCase(lastNameDB)) {
                String firstNameDB = candidateDB.getFirstName();
                if (firstName.equalsIgnoreCase(firstNameDB)) {
                    isDuplicate = true;
                }
            }
        }

        if (isDuplicate) {
            return candidate;
        }

        else {
           return createCandidate(candidate);
        }
    }
}
