package fr.ecolnum.projectapi.CandidateTest;


import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.ResultImportListDto;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotCsvException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "homePath=/home/aurelien/Documents/java/project-api"
})
public class CandidateServiceUnitTest {

    private final CandidateRepository candidateRepository;

    private final CandidateService candidateService;

    private final MockMultipartFile csvMock;
    private final MockMultipartFile archiveMock;

    public CandidateServiceUnitTest() throws IOException {
        this.candidateRepository = Mockito.mock(CandidateRepository.class);
        this.candidateService = new CandidateService("/home/aurelien/Documents/java/project-api",candidateRepository);

        //as test are always launch in home folder, we can put it without absolute path
        InputStream archiveFile = new FileInputStream("src/test/resources/test/candidate/test.zip");
        //name correspond to the @RequestPart field name (by default, variable name)
        String csvContent = """
                #QuelqueChose;autreChose;image
                                
                prénom;nom;32195.png
                prénom2;nom2;unnamed.png
                prénom3;nom3;candidat.json
                prénom4;nom4;candidate.html
                prénom5;nom5;toto.txt
                prénom8;nom8;54833.png
                #voluntarly missing a field
                prénom6;nom7
                """;
        csvMock = new MockMultipartFile("listCsv", "test.csv", "text/csv", csvContent.getBytes());
        archiveMock = new MockMultipartFile("photoFolder", "test.zip", "application/zip", archiveFile.readAllBytes());
//        this.homePath = homePath;

    }

    @Test
    public void importCandidateList() throws Exception {
//        ReflectionTestUtils.setField(candidateService, "homePath", "/home/aurelien/Documents/java/project-api");

        Candidate firstCandidate = new Candidate("prénom", "nom");
        Candidate firstCandidateWithId = new Candidate(-1, "prénom", "nom");
        Candidate firstCandidateWithIdAndPhoto = new Candidate(-1, "prénom", "nom", ".png", null);

        Candidate secondCandidate = new Candidate("prénom2", "nom2");
        Candidate secondCandidateWithId = new Candidate(-2, "prénom2", "nom2");
        Candidate secondCandidateWithIdAndPhoto = new Candidate(-2, "prénom2", "nom2", ".png", null);

        when(candidateRepository.findByLastNameEquals(any()))
                .thenReturn(
                        new ArrayList<>()
                );
        when(candidateRepository.findByLastNameEquals(eq("nom8")))
                .thenReturn(
                        List.of(
                                new Candidate("prénom8","nom8")
                        )
                );
        //eq function needs an override of .equals() method in Candidate class
        when(candidateRepository.save(eq(firstCandidate)))
                .thenReturn(
                        firstCandidateWithId
                );
        when(candidateRepository.save(firstCandidateWithId))
                .thenReturn(
                        firstCandidateWithIdAndPhoto
                );

        when(candidateRepository.save(eq(secondCandidate)))
                .thenReturn(
                        secondCandidateWithId
                );
        when(candidateRepository.save(secondCandidateWithId))
                .thenReturn(
                        secondCandidateWithIdAndPhoto
                );


        ResultImportListDto functionResult = candidateService.importCandidateList(csvMock, archiveMock);
        Iterable<CandidateDto> sucessfull = functionResult.getImported(),
                failedDuplicate = functionResult.getDuplicate(),
                failedNoPhoto = functionResult.getPhotoMissing();


        assertEquals(getIterableSize(sucessfull), 2);

        assertEquals(getIterableSize(failedDuplicate), 1);

        assertEquals(getIterableSize(failedNoPhoto), 3);
    }

    private static <E> int getIterableSize(Iterable<E> iterable) {
        int cpt = 0;
        for (E element : iterable) {
            cpt++;
        }
        return cpt;
    }

    @Test
    public void importCandidateListNotCsvFile() throws Exception {
        MultipartFile csvMock = Mockito.mock(MultipartFile.class);
        when(csvMock.getContentType())
                .thenReturn("tata/Citi");

        assertThrows(MultipartFileIsNotCsvException.class, () -> candidateService.importCandidateList(csvMock, archiveMock));
    }
}
