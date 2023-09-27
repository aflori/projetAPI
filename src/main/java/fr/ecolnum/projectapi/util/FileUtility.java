package fr.ecolnum.projectapi.util;

import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {

    public static void changeFileName(String oldName, String newName) {
        File oldF = new File(oldName);
        File newF = new File(newName);
        oldF.renameTo(newF);
    }

    public static String extractPhotoExtension(MultipartFile photoCandidate) throws MultipartFileIsNotImageException {

        String photoType = photoCandidate.getContentType();
        if (photoType == null) {
            throw new MultipartFileIsNotImageException("no file found");
        }
        String fileType = photoType.substring(0, 5);
        String fileExtension = photoType.substring(6);

        if (fileType.equals("image")) {
            return '.' + fileExtension;
        }
        throw new MultipartFileIsNotImageException("not an image file");
    }

    public static void writePhotoIn(MultipartFile photoCandidate, String fileName) throws FileNotUpdatableException {
        try {
            FileOutputStream photoFile = new FileOutputStream(fileName, false);
            byte[] photoData = photoCandidate.getBytes();
            photoFile.write(photoData);
            photoFile.close();
        } catch (IOException e) {
            //can't happen, File class did create file
            throw new FileNotUpdatableException();
        }
    }

    public static void createEmptyFileByName(String fileName) throws FileNotUpdatableException {
        try {
            File creationFile = new File(fileName);
            if (creationFile.exists()) {
                creationFile.delete();
            }
            creationFile.createNewFile();

        } catch (Exception e) {
            //could not create file
            throw new FileNotUpdatableException();
        }
    }
}
