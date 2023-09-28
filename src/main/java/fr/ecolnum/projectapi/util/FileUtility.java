package fr.ecolnum.projectapi.util;

import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {

    public static void changeFileName(File homeFolder, String oldName, String newName) {
        File oldF = new File(homeFolder, oldName);
        File newF = new File(homeFolder, newName);
        oldF.renameTo(newF);
    }

    public static String checkAndExtractPhotoExtension(MultipartFile photoCandidate) throws MultipartFileIsNotImageException {

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

    public static void writePhotoIn(MultipartFile photoCandidate, File emptyFile) throws FileNotUpdatableException {
        try {
            FileOutputStream photoFile = new FileOutputStream(emptyFile, false);
            byte[] photoData = photoCandidate.getBytes();
            photoFile.write(photoData);
            photoFile.close();
        } catch (IOException e) {
            //can't happen, File class did create file
            throw new FileNotUpdatableException();
        }
    }

    public static File createEmptyFileByName(File homeFolder, String fileName) throws FileNotUpdatableException {
        try {
            File creationFile = new File(homeFolder, fileName);
            if (creationFile.exists()) {
                creationFile.delete();
            }
            creationFile.createNewFile();
            return creationFile;

        } catch (Exception e) {
            //could not create file
            throw new FileNotUpdatableException();
        }
    }
}
