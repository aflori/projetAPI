package fr.ecolnum.projectapi.util;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GenericUtilityTest {

    @InjectMocks
    private UtilDebug utilityInterface;

    //must have the same name as utility attribute
    @Mock
    private CandidateRepository repository;

    @Test
    public void testExtractSetFromRepository() {
        List<Integer> listIdToTest = new ArrayList<>(List.of(1, 2, 3));

        when(repository.findById(1))
                .thenReturn(
                        Optional.of(
                                new Candidate(1, "A", "B")
                        )
                );
        when(repository.findById(2))
                .thenReturn(
                        Optional.of(
                                new Candidate(2, "A2", "B2")
                        )
                );
        when(repository.findById(3))
                .thenReturn(
                        Optional.of(
                                new Candidate(3, "C", "Cc")
                        )
                );
        when(repository.findById(8))
                .thenReturn(
                        Optional.empty()
                );

        Set<Candidate> setResult = utilityInterface.testGenericUtility(listIdToTest);

        assertEquals(setResult.size(), 3);

        listIdToTest.add(8);

        try {
            utilityInterface.testGenericUtility(listIdToTest);
            fail();
        } catch (RuntimeException ignore) {

        }
    }
}
