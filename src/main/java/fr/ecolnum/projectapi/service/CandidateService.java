package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.exception.*;
import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.ResultImportListDto;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.GroupRepository;
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
    private final static String photoPath = "/assets/candidatePhoto/";
    private final static String temporaryPhotoFolder = "/assets/tempPhoto/";

    // get the project directory from application.properties
    private String homePath;

    private CandidateRepository candidateRepository;
    private GroupRepository groupRepository;

    public CandidateService(@Value("${homePath}") String homePath,
                            @Autowired CandidateRepository candidateRepository,
                            @Autowired GroupRepository groupRepository) {
        this.homePath = homePath;
        this.candidateRepository = candidateRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * this method create a candidate in the database and import a photo in local
     *
     * @param firstName      candidate's first  name
     * @param lastName       candidate's last name
     * @param photoCandidate photo object of the associated candidate
     * @return the candidate (with its new ID and photo pass) created
     */
    public CandidateDto createCandidate(String firstName, String lastName, MultipartFile photoCandidate) throws MultipartFileIsNotImageException, FileNotUpdatableException {
        String temporaryFolderName = homePath + temporaryPhotoFolder;
        File temporaryFolder = new File(temporaryFolderName);
        String extension = checkAndExtractPhotoExtension(photoCandidate);

        File temporaryPhotoFile = createEmptyFileByName(temporaryFolder, firstName + '_' + lastName + extension);
        writePhotoIn(photoCandidate, temporaryPhotoFile);

        CandidateDto candidateDto = createCandidate(firstName, lastName, temporaryPhotoFile);

        temporaryPhotoFile.delete();

        return candidateDto;

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
            Candidate actualCandidate = new Candidate(firstName,lastName);
            if (actualCandidate.equals(dbCandidate)) {
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
    public ResultImportListDto importCandidateList(MultipartFile csvFile, MultipartFile photoZip) throws MultipartFileIsNotCsvException, MultipartFileIsNotAnArchiveException, IOException {


        if (!csvFile.getContentType().equals("text/csv")) {
            throw new MultipartFileIsNotCsvException();
        }

        String tempFolderName = homePath + temporaryPhotoFolder;
        Path path = Paths.get(tempFolderName);
        try {
            //extract data from multipartFile
            Set<String[]> listCandidateImported = parseCsvFile(csvFile);
            Map<String, File> listPhotoByName = getPhotoFromZipArchive(photoZip, path);

            //initializing return field (int variable means "hey, my sets should not have more than maxSize element")
            int maxSize = listCandidateImported.size();
            Set<CandidateDto> importedCandidate = new HashSet<>(maxSize);
            Set<CandidateDto> duplicateCandidate = new HashSet<>(maxSize);
            Set<CandidateDto> candidateWithoutPhoto = new HashSet<>(maxSize);

            runThroughCsvCandidateAndInitializeSet(listCandidateImported, listPhotoByName, candidateWithoutPhoto, duplicateCandidate, importedCandidate);

            //removing unused file in temporary folder (except .gitkeep)
            File tempFolder = new File(tempFolderName);
            deleteFolderContentExcept(tempFolder,
                    (file) -> ".gitkeep".equals(file.getName())
            );

            return new ResultImportListDto(importedCandidate, duplicateCandidate, candidateWithoutPhoto);
        } catch (FileNotUpdatableException e) {
            throw new IOException(e);
        } catch (MultipartFileIsNotImageException ignored) {
            return null;
        }

    }

    private void runThroughCsvCandidateAndInitializeSet(Set<String[]> listCandidate, Map<String, File> mapPhotoByName,
                                                        Set<CandidateDto> setToInitializeCandidateNoPhoto,
                                                        Set<CandidateDto> setToInitializeCandidateDuplicate,
                                                        Set<CandidateDto> setToInitializeImportedCandidate) throws MultipartFileIsNotImageException {
        for (String[] candidateData : listCandidate) {

            if (isNotACandidate(candidateData)) {
                continue;  // wi ignore the line
            }
            //initializing datas
            String firstName = candidateData[0];
            String lastName = candidateData[1];
            File photoFile = mapPhotoByName.get(candidateData[2]);

            if (photoFile == null) {

                setToInitializeCandidateNoPhoto.add(new CandidateDto(firstName, lastName));

            } else if (hasDuplicate(firstName, lastName)) {

                setToInitializeCandidateDuplicate.add(new CandidateDto(firstName, lastName));

            } else {
                //importing candidate
                CandidateDto candidateAdded = this.createCandidate(firstName, lastName, photoFile);
                setToInitializeImportedCandidate.add(candidateAdded);

                //the photo does not exist in temporary file anymore
                mapPhotoByName.remove(candidateData[2]);
            }
        }
    }

    private static boolean isNotACandidate(String[] candidateData) {
        return candidateData.length != 3;
    }

    protected CandidateDto createCandidate(String firstName, String lastName, File photo) throws MultipartFileIsNotImageException {

        Candidate candidate = new Candidate(firstName, lastName);


        if (DEBUG) {
            if (photo == null) {
                Candidate createdCandidate = candidateRepository.save(candidate);
                return new CandidateDto(createdCandidate);
            }
        }
        if (!photo.exists()) {
            throw new MultipartFileIsNotImageException();
        }

        File homeFolder = new File(homePath + photoPath);

        String extensionPhoto = checkAndExtractPhotoExtension(photo);

        Candidate newCandidateSaved = candidateRepository.save(candidate);

        String photoName = newCandidateSaved.getFirstName() + '_' + newCandidateSaved.getLastName() + '_' + newCandidateSaved.getId() + extensionPhoto;

        File newFile = changeFileName(photo, homeFolder, photoName);

        newCandidateSaved.setPhotoName(photoPath + newFile.getName());

        newCandidateSaved = candidateRepository.save(newCandidateSaved); // saved photoName

        return new CandidateDto(newCandidateSaved);

    }
}
