package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static fr.ecolnum.projectapi.util.FileUtility.*;

/**
 * Service for candidate action
 */
@Service
public class CandidateService {

    private final static boolean DEBUG = true;
    private final static String photoPath = "assets/candidatePhoto/";

    // get the project directory from application.properties
    @Value("${homePath}")
    private String homePath;

    private String homePath2 = "\\Documents\\Programmes\\java\\projetAPI";

    private final boolean isOnWindowsOS = true;


    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * this method create a candidate in the database and import a photo in local
     *
     * @param candidate      candidate to be created in database
     * @param photoCandidate photo object of the associated candidate
     * @return the candidate (with its new ID and photo URL) created
     */
    public CandidateDto createCandidate(String firstName, String lastName, MultipartFile photoCandidate) throws MultipartFileIsNotImageException, FileNotUpdatableException {

        // create a file for the project directory
        Candidate candidate = new Candidate(firstName, lastName);

        if(this.isOnWindowsOS){
            homePath ="C:" + homePath + homePath2;
        }
        File homeFolder = new File(homePath);

        if (DEBUG == true) {
            if (photoCandidate == null) {
                Candidate createdCandidate = candidateRepository.save(candidate);
                return new CandidateDto(createdCandidate);
            }
        }

        String extensionPhoto = checkAndExtractPhotoExtension(photoCandidate);

        String fileName = candidate.getFirstName() + '_' + candidate.getLastName() + '_';
        String directoryFileName = photoPath + fileName;

        File emptyFile = createEmptyFileByName(homeFolder, directoryFileName);
        writePhotoIn(photoCandidate, emptyFile);


        Candidate newCandidateSaved = candidateRepository.save(candidate);

        //update the filename of the photo with the id of the candidate once he is created and his extension.
        String newFileName = fileName + candidate.getId() + extensionPhoto;
        String newDirectoryFileName = photoPath + newFileName;

        changeFileName(homeFolder, directoryFileName, newDirectoryFileName);

        newCandidateSaved.setPhotoName(newFileName);

        Candidate createdCandidate = candidateRepository.save(newCandidateSaved);
        return new CandidateDto(createdCandidate);
    }

    /**
     * @param firstName      firstName of Candidate
     * @param lastName       lastName of Candidate
     * @param photoCandidate photo of Candidate
     * @return return the new candidate from the database
     * @throws CandidateAlreadyExistsException if there is a duplicate
     */
    public CandidateDto checkDuplicate(String firstName,
                                       String lastName,
                                       MultipartFile photoCandidate) throws CandidateAlreadyExistsException, MultipartFileIsNotImageException, FileNotUpdatableException {

        boolean isDuplicate = false;

        Iterable<Candidate> candidateList = candidateRepository.findByLastNameEquals(lastName);

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
            CandidateDto candidateToCreate = new CandidateDto(firstName, lastName);
            return createCandidate(candidateToCreate.getFirstName(), candidateToCreate.getLastName(), photoCandidate);
        }
    }

    /**
     *  service to return a list of candidate that has the same first name  and last name as the one given in parameter
     * @param candidate the referent candidate
     * @return a list of candidate with the same first name and last name
     */
    public Iterable<CandidateDto> returnDuplicate(CandidateDto candidate) {
        String lastName = candidate.getLastName();
        String firstName = candidate.getFirstName();
        Iterable<Candidate> candidateList = candidateRepository.findByLastNameEquals(lastName);

        Set<CandidateDto> duplicateCandidate = new HashSet<>();

        for (Candidate dbCandidate : candidateList) {
            String firstNameDB = dbCandidate.getFirstName();
            if (firstName.equalsIgnoreCase(firstNameDB)) {
                duplicateCandidate.add(new CandidateDto(dbCandidate));
            }
        }

        return duplicateCandidate;
    }

    /**
     * function made to return a list of candidate present in the repository
     * @return a list of all existing candidate
     */
    public Iterable<CandidateDto> getAllCandidate() {
        Iterable<Candidate> allCandidate = candidateRepository.findAll();
        Set<CandidateDto> allCandidateDto = new HashSet<>();

        for (Candidate candidate : allCandidate) {
            allCandidateDto.add(new CandidateDto(candidate));
        }
        return allCandidateDto;
    }

    /**
     *  function made to return a specific candidate
     * @param id id of the wanted candidate
     * @return the candidate with the parameter id
     * @throws IdNotFoundException if the id is not given in database
     */
    public CandidateDto getCandidateById(int id) throws IdNotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);

        if (candidate.isEmpty()) {
            throw new IdNotFoundException();
        }
        return new CandidateDto(candidate.get());
    }
}
