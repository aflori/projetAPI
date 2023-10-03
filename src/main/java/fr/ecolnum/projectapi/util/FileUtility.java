package fr.ecolnum.projectapi.util;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotAnArchiveException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import jakarta.activation.MimetypesFileTypeMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtility {


    public static File changeFileName(File oldF, File newFileFolder, String newFileName) {
        File newF = new File(newFileFolder, newFileName);
        oldF.renameTo(newF);

        return newF;
    }

    public static String checkAndExtractPhotoExtension(MultipartFile photoCandidate) throws MultipartFileIsNotImageException {

        String photoType = photoCandidate.getContentType();
        if (photoType == null) {
            throw new MultipartFileIsNotImageException("no file found");
        }
        return getPhotoExtension(photoType);
    }

    protected static String getPhotoExtension(String fileName) throws MultipartFileIsNotImageException {

        if (fileName.startsWith("image")) {
            String fileExtension = fileName.substring(6);
            return '.' + fileExtension;
        }
        throw new MultipartFileIsNotImageException("not an image file");
    }

    public static String checkAndExtractPhotoExtension(File photoCandidate) throws MultipartFileIsNotImageException {
        MimetypesFileTypeMap typeFinder = new MimetypesFileTypeMap();
        String fileType = typeFinder.getContentType(photoCandidate);
        return getPhotoExtension(fileType);
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

    public static Map<String, File> getPhotoFromZipArchive(MultipartFile zipFileContainingPhoto, Path basePath) throws MultipartFileIsNotAnArchiveException, FileNotUpdatableException {
        if (!"application/zip".equals(zipFileContainingPhoto.getContentType())) {
            throw new MultipartFileIsNotAnArchiveException();
        }


        try {
            ZipInputStream zipContent = new ZipInputStream(zipFileContainingPhoto.getInputStream());

            return runThroughZipContentAndGetPhotoMap(basePath, zipContent);

        } catch (IOException e) {
            throw new FileNotUpdatableException();
        }

    }

    public static Set<String[]> parseCsvFile(MultipartFile csvFile) throws IOException {
        Reader reader = new InputStreamReader(csvFile.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build()
                ).build();

        List<String[]> allLines = csvReader.readAll();
        Set<String[]> allLinesWithoutComment = new HashSet<>(allLines.size());

        for (String[] line : allLines) {
            if (isEmptyLine(line) || isCommentLine(line)) {
                continue;
            }
            allLinesWithoutComment.add(line);
        }
        return allLinesWithoutComment;

    }

    protected static boolean isEmptyLine(String[] line) {
        return line.length == 0 || (line.length == 1 && line[0].isEmpty());
    }

    protected static boolean isCommentLine(String[] line) {
        return line[0].charAt(0) == '#';
    }

    protected static Map<String, File> runThroughZipContentAndGetPhotoMap(Path basePath, ZipInputStream zip) throws IOException {
        Map<String, File> listOfPhotoMappedByName = new HashMap<>();
        MimetypesFileTypeMap typeFinder = new MimetypesFileTypeMap();


        for (ZipEntry singlePhotoInZip = zip.getNextEntry(); singlePhotoInZip != null; singlePhotoInZip = zip.getNextEntry()) {

            String photoType = typeFinder.getContentType(singlePhotoInZip.getName());

            //ignore non-image file
            if (!photoType.startsWith("image")) {
                continue;
            }
            extractPhotoFromZip(basePath, singlePhotoInZip.getName(), zip, listOfPhotoMappedByName);
        }
        return listOfPhotoMappedByName;
    }

    protected static void extractPhotoFromZip(Path basePath, String photoName, ZipInputStream zipFile, Map<String, File> mapToUpdate) throws IOException {
        Path photoPath = basePath.resolve(photoName);   // creating good path
        Files.createDirectories(photoPath.getParent()); // creating parent repository if not exist
        Files.copy(zipFile, photoPath);                 // importing variable file to goof folder


        mapToUpdate.put(photoName, new File(photoPath.toUri()));
    }

    public static void deleteFolderContent(File folder) {
        File[] files = folder.listFiles();
        if(files!= null) {
            for(File file: files) {
                if(file.getName().equals(".gitkeep")) {
                    continue;
                }

                if(file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    public static void deleteFolder(File folder) {
        deleteFolderContent(folder);
        folder.delete();
    }
}
