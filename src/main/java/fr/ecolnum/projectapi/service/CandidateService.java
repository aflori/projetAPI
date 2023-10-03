package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.*;
import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.OptionnalCandidateDto;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static fr.ecolnum.projectapi.util.FileUtility.*;

/**
 * Service for candidate action
 */
@Service
public class CandidateService {

    private final static boolean DEBUG = true;
    private final static String photoPath = "assets/candidatePhoto/";
    private final static String temporaryPhotoFolder = "assets/tempPhoto/";

    // get the project directory from application.properties
    @Value("${homePath}")
    private String homePath;

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * this method create a candidate in the database and import a photo in local
     *
     * @param firstName      candidate's first  name
     * @param lastName       candidate's last name
     * @param photoCandidate photo object of the associated candidate
     * @return the candidate (with its new ID and photo URL) created
     */
    public CandidateDto createCandidate(String firstName, String lastName, MultipartFile photoCandidate) throws MultipartFileIsNotImageException, FileNotUpdatableException {

        // create a file for the project directory
        Candidate candidate = new Candidate(firstName, lastName);

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
    public CandidateDto registerCandidateIfNotDuplicate(String firstName,
                                                        String lastName,
                                                        MultipartFile photoCandidate) throws CandidateAlreadyExistsException, MultipartFileIsNotImageException, FileNotUpdatableException {


        if (hasDuplicate(firstName, lastName)) {
            throw new CandidateAlreadyExistsException("This name is already used");
        }

        return createCandidate(firstName, lastName, photoCandidate);
    }


    protected boolean hasDuplicate(String firstName, String lastName) {
        Iterable<Candidate> candidateList = candidateRepository.findByLastNameEquals(lastName);


        for (Candidate candidateDB : candidateList) {

            if (isSamePerson(firstName, lastName, candidateDB)) return true;

        }
        return false;
    }

    private boolean isSamePerson(String firstName, String lastName, Candidate candidateDB) {
        String firstNameCandidate = candidateDB.getFirstName();
        String lastNameDBCandidate = candidateDB.getLastName();

        return firstName.equalsIgnoreCase(firstNameCandidate) && lastName.equalsIgnoreCase(lastNameDBCandidate);
    }


    /**
     * service to return a list of candidate that has the same first name  and last name as the one given in parameter
     *
     * @param candidate the referent candidate
     * @return a list of candidate with the same first name and last name
     */
    public Iterable<CandidateDto> returnAllDuplicates(CandidateDto candidate) {
        String lastName = candidate.getLastName();
        String firstName = candidate.getFirstName();
        Iterable<Candidate> candidateList = candidateRepository.findByLastNameEquals(lastName);

        Set<CandidateDto> duplicateCandidate = new HashSet<>();

        for (Candidate dbCandidate : candidateList) {
            if (this.isSamePerson(firstName, lastName, dbCandidate)) {
                duplicateCandidate.add(new CandidateDto(dbCandidate));
            }
        }

        return duplicateCandidate;
    }

    /**
     * function made to return a list of candidate present in the repository
     *
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
     * function made to return a specific candidate
     *
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

    /**
     * service made to import a list of candidates
     *
     * @param csvFile  correspond to the csv file containing all information to the candidates imported
     * @param photoZip represent the zip package containing all photos of the candidates
     * @return list of candidateDto imported with a boolean indicating if candidate is already in dataBase
     * @throws MultipartFileIsNotCsvException       if variable csvFile is not a csv
     * @throws MultipartFileIsNotAnArchiveException if photoZip is not an archive
     * @throws IOException                          if file creation bugged
     */
    public List<OptionnalCandidateDto> importCandidateList(MultipartFile csvFile, MultipartFile photoZip) throws MultipartFileIsNotCsvException, MultipartFileIsNotAnArchiveException, IOException {


        if (!csvFile.getContentType().equals("text/csv")) {
            throw new MultipartFileIsNotCsvException();
        }

        Path path = Paths.get(homePath + '/' + temporaryPhotoFolder);

        try {
            Set<String[]> listCandidateImported = parseCsvFile(csvFile);
            Map<String, File> listPhotoByName = getPhotoFromZipArchive(photoZip, path);
            List<OptionnalCandidateDto> list = new ArrayList<>();

            for (String[] candidateData : listCandidateImported) {

                String firstName = candidateData[0];
                String lastName = candidateData[1];
                File photoFile = listPhotoByName.get(candidateData[2]);
                list.add(this.createCandidate(firstName, lastName, photoFile));
            }

            return list;
        } catch (FileNotUpdatableException e) {
            throw new IOException(e);
        } catch (MultipartFileIsNotImageException ignored) {
            return null;
        }

    }

    protected OptionnalCandidateDto createCandidate(String firstName, String lastName, File photo) throws MultipartFileIsNotImageException {

        Candidate candidate = new Candidate(firstName, lastName);

        File homeFolder = new File(homePath);

        if (DEBUG == true) {
            if (photo == null) {
                Candidate createdCandidate = candidateRepository.save(candidate);
                return new OptionnalCandidateDto(createdCandidate);
            }
        }

        String extensionPhoto = checkAndExtractPhotoExtension(photo);

//        String fileName = candidate.getFirstName() + '_' + candidate.getLastName() + '_';
//        String directoryFileName = photoPath + fileName;
//
//        File emptyFile = createEmptyFileByName(homeFolder, directoryFileName);
//        writePhotoIn(photoCandidate, emptyFile);
//
//
//        Candidate newCandidateSaved = candidateRepository.save(candidate);
//
//        //update the filename of the photo with the id of the candidate once he is created and his extension.
//        String newFileName = fileName + candidate.getId() + extensionPhoto;
//        String newDirectoryFileName = photoPath + newFileName;
//
//        changeFileName(homeFolder, directoryFileName, newDirectoryFileName);
//
//        newCandidateSaved.setPhotoName(newFileName);
//
//        Candidate createdCandidate = candidateRepository.save(newCandidateSaved);
//        return new OptionnalCandidateDto(firstName, lastName, true);
        return null;
    }
}
