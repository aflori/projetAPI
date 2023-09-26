package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        if(photoCandidate==null) {
            return repository.save(candidate);
        }
        String extensionPhoto = extractPhotoExtension(photoCandidate);

        String fileName = "candidatePhoto/" + candidate.getFirstName() + '_' + candidate.getLastName() + '_';

        createEmptyFileByName(fileName);
        writePhotoIn(photoCandidate, fileName);

        candidate.setPhotoUrl(fileName);

        Candidate newCandidateSaved = repository.save(candidate);

        String newFileName = fileName + candidate.getId() + extensionPhoto;

        createEmptyFileByName(newFileName);
        changeFileName(fileName, newFileName);

        newCandidateSaved.setPhotoUrl(newFileName);

        return repository.save(newCandidateSaved);
    }



}
