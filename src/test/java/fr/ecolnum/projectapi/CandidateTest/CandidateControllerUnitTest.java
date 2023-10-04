package fr.ecolnum.projectapi.CandidateTest;


import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.ResultImportListDto;
import fr.ecolnum.projectapi.controller.CandidateController;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotAnArchiveException;
import fr.ecolnum.projectapi.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CandidateController.class)
public class CandidateControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${homePath}")
    private String homePath;

    @MockBean
    private CandidateService candidateService;

    @Test
    public void testGetOrdersList() throws Exception {

        InputStream csvFile = new FileInputStream("src/test/resources/test/candidate/test.csv");
        InputStream archiveFile = new FileInputStream("src/test/resources/test/candidate/test.zip");

        //name correspond to the @RequestPart field name (by default, variable name)
        MockMultipartFile csvMock = new MockMultipartFile("listCsv", csvFile);
        MockMultipartFile archiveMock = new MockMultipartFile("photoFolder", archiveFile);

        when(candidateService.importCandidateList(csvMock, archiveMock))
                .thenReturn(new ResultImportListDto(
                        Set.of(
                                new CandidateDto("alpha", "beta"),
                                new CandidateDto("gamma", "delta"),
                                new CandidateDto("theta", "iota")
                        ),
                        Set.of(
                                new CandidateDto("espilone", "dzeta")
                        ),
                        Set.of(
                                new CandidateDto("kappa", "lambda"),
                                new CandidateDto("mu", "nu")
                        )
                ));
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/api/admin/candidate/list")
                                .file(csvMock)
                                .file(archiveMock)
                )
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.imported").isArray())
                .andExpect(jsonPath("$.imported", hasSize(3)))
                .andExpect(jsonPath("$.duplicate").isArray())
                .andExpect(jsonPath("$.duplicate", hasSize(1)))
                .andExpect(jsonPath("$.photoMissing").isArray())
                .andExpect(jsonPath("$.photoMissing", hasSize(2)));
    }

    @Test
    public void testGetOrdersListMultipartException() throws Exception {

        InputStream csvFile = new FileInputStream("src/test/resources/test/candidate/test.csv");
        InputStream archiveFile = new FileInputStream("src/test/resources/test/candidate/test.zip");

        //name correspond to the @RequestPart field name (by default, variable name)
        MockMultipartFile csvMock = new MockMultipartFile("listCsv", csvFile);
        MockMultipartFile archiveMock = new MockMultipartFile("photoFolder", archiveFile);

        when(candidateService.importCandidateList(csvMock, archiveMock))
                .thenThrow(new MultipartFileIsNotAnArchiveException("toto"));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/api/admin/candidate/list")
                                .file(csvMock)
                                .file(archiveMock)
                )
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();

        String contentResult = result.getResponse().getContentAsString();
        assertEquals(contentResult, "toto");
    }

    @Test
    public void testGetOrdersListIOException() throws Exception {

        InputStream csvFile = new FileInputStream("src/test/resources/test/candidate/test.csv");
        InputStream archiveFile = new FileInputStream("src/test/resources/test/candidate/test.zip");

        //name correspond to the @RequestPart field name (by default, variable name)
        MockMultipartFile csvMock = new MockMultipartFile("listCsv", csvFile);
        MockMultipartFile archiveMock = new MockMultipartFile("photoFolder", archiveFile);

        when(candidateService.importCandidateList(csvMock, archiveMock))
                .thenThrow(new IOException("titi"));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/api/admin/candidate/list")
                                .file(csvMock)
                                .file(archiveMock)
                )
                .andExpect(status().isInternalServerError())
                .andReturn();

        String contentResult = result.getResponse().getContentAsString();
        assertEquals(contentResult, "titi");
    }
}
