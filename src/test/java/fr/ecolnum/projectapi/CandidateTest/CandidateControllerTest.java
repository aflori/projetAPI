package fr.ecolnum.projectapi.CandidateTest;


import fr.ecolnum.projectapi.controller.CandidateController;
import fr.ecolnum.projectapi.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @InjectMocks
    private CandidateController candidateController;
    @Mock
    private CandidateService candidateService;

    @Test
    public void testImportList() throws Exception {
        //no when yet

        ResponseEntity<?> answer = candidateController.importList(null,null);

        assert (answer.getStatusCode() == HttpStatus.CREATED);
    }
}
