package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

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


    public Candidate importPhotoToCandidate(int idCandidate, MultipartFile photoCandidate) {
        Optional<Candidate> optionalCandidate = repository.findById(idCandidate);

        if (optionalCandidate.isEmpty()) {
            return null;
        }
        Candidate candidate = optionalCandidate.get();

        String photoType = photoCandidate.getContentType();

        String extensionPhoto = extractExtension(photoType);

        String fileName = "candidatePhoto/" + candidate.getFirstName() + '_' + candidate.getLastName() + '_' + candidate.getId() + extensionPhoto;

        createEmptyFileByName(fileName);

        writePhotoIn(photoCandidate, fileName);

        return candidate;
    }

    private static String extractExtension(String fileTypeDescription) {
        String fileType = fileTypeDescription.substring(0, 5);
        String fileExtension = fileTypeDescription.substring(6);

        if (fileType.equals("image")) {
            return '.' + fileExtension;
        }
        throw new RuntimeException();
    }

    private static void writePhotoIn(MultipartFile photoCandidate, String fileName) {
        try {
            FileOutputStream photoFile = new FileOutputStream(fileName, false);
            byte[] photoData = photoCandidate.getBytes();
            photoFile.write(photoData);
            photoFile.close();
        } catch (IOException e) {
            //can't happen, File class did create file
            throw new RuntimeException(e);
        }
    }

    private static void createEmptyFileByName(String fileName) {
        try {
            File creationFile = new File(fileName);
            if (creationFile.exists()) {
                creationFile.delete();
            }
            creationFile.createNewFile();

        } catch (Exception e) {
            //could not create file
            throw new RuntimeException(e);
        }
    }
}
