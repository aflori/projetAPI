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

        if(optionalCandidate.isEmpty()) {
            return null;
        }
        Candidate candidate = optionalCandidate.get();

        String  fileName = candidate.getFirstName() + '_' + candidate.getLastName() + '_' + candidate.getId();

        if (createEmptyFileByName(fileName)) return null;

        writePhotoIn(photoCandidate, fileName);

        return candidate;
    }

    private static void writePhotoIn(MultipartFile photoCandidate, String fileName) {
        try {
            FileOutputStream photoFile = new FileOutputStream(fileName,false);
            byte[] photoData = photoCandidate.getBytes();
            photoFile.write(photoData);
            photoFile.close();
        } catch (IOException e) {
            //can't happen, File class did create file
            throw new RuntimeException(e);
        }
    }

    private static boolean createEmptyFileByName(String fileName) {
        try {
            File creationFile = new File(fileName);
            if(creationFile.exists()) {
                creationFile.delete();
            }
            creationFile.createNewFile();

        } catch (Exception e) {
            //could not create file
            return true;
        }
        return false;
    }
}
