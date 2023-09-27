package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @param firstName
     * @param lastName
     * @param photoCandidate
     * @return
     */
    public Candidate createCandidate(String firstName,
                                     String lastName,
                                     MultipartFile photoCandidate) {
        return null;
    }


    /**
     *
     * @param firstName firstName of Candidate
     * @param lastName lastName of Candidate
     * @param photoCandidate photo of Candidate
     * @return return the new candidate from the database
     * @throws CandidateAlreadyExistsException if there is a duplicate
     */
    public Candidate checkDuplicate(String firstName,
                                    String lastName,
                                    MultipartFile photoCandidate) throws CandidateAlreadyExistsException {

        boolean isDuplicate = false;

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
            return createCandidate(firstName, lastName, photoCandidate);

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
