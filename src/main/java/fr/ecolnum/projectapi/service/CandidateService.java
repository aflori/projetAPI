package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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


    /**
     * check for duplicate candidate before creation.
     *
     * @param candidate candidate checked to see if it already exists
     * @return the candidate created
     */
    public Candidate checkDuplicate(Candidate candidate) throws CandidateAlreadyExistsException {

        boolean isDuplicate = false;

        String lastName = candidate.getLastName();
        String firstName = candidate.getFirstName();
        Iterable<Candidate> candidateList = repository.findByLastNameEquals(lastName);

        Iterator<Candidate> iter;
        iter = candidateList.iterator();

        while (iter.hasNext() && !isDuplicate) {
            Candidate candidateDB = iter.next();
            String firstNameDB = candidateDB.getFirstName();
            if (firstName.equalsIgnoreCase(firstNameDB)) {
                isDuplicate = true;
            }
        }

        if (isDuplicate) {
            throw new CandidateAlreadyExistsException("This name is already used");
        } else {
            return createCandidate(candidate);

        }
    }

    public Iterable<Candidate> returnDuplicate(Candidate candidate) {
        String lastName = candidate.getLastName();
        String firstName = candidate.getFirstName();
        Iterable<Candidate> candidateList = repository.findByLastNameEquals(lastName);

        Set<Candidate> duplicateCandidate = new HashSet<>();

        for (Candidate dbCandidate : candidateList) {
            String firstNameDB = dbCandidate.getFirstName();
            if (firstName.equalsIgnoreCase(firstNameDB)) {
                duplicateCandidate.add(dbCandidate);
            }
        }

        return duplicateCandidate;
    }
}
