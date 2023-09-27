package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static fr.ecolnum.projectapi.util.FileUtility.*;

/**
 * Service for candidate action
 */
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository repository;

    /**
     * this method create a candidate in the database and import a photo in local
     *
     * @param candidate candidate to be created in database
     * @param photoCandidate photo object of the associated candidate
     *
     * @return the candidate (with its new ID and photo URL) created
     */
    public Candidate createCandidate(Candidate candidate, MultipartFile photoCandidate) throws MultipartFileIsNotImageException, FileNotUpdatableException {

        if (photoCandidate == null) {
            return repository.save(candidate);
        }
        String extensionPhoto = extractPhotoExtension(photoCandidate);

        String fileName = candidate.getFirstName() + '_' + candidate.getLastName() + '_';
        String directoryFileName = "assets/candidatePhoto/" + fileName;

                createEmptyFileByName(directoryFileName);
        writePhotoIn(photoCandidate, directoryFileName);

        candidate.setPhotoUrl(fileName);

        Candidate newCandidateSaved = repository.save(candidate);

        String newFileName = fileName + candidate.getId() + extensionPhoto;
        String newDirectoryFileName = "assets/candidatePhoto/" + newFileName;

        createEmptyFileByName(newDirectoryFileName);
        changeFileName(directoryFileName, newDirectoryFileName);

        newCandidateSaved.setPhotoUrl(newFileName);

        return repository.save(newCandidateSaved);
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
                                    MultipartFile photoCandidate) throws CandidateAlreadyExistsException, MultipartFileIsNotImageException, FileNotUpdatableException {

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
            Candidate candidateToCreate = new Candidate(firstName, lastName);
            return createCandidate(candidateToCreate, photoCandidate);
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
