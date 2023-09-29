package fr.ecolnum.projectapi.utilTest;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GenericUtilityTest {

    //object which must be simulated
    @Mock
    private CandidateRepository repository;

    @Test
    public void testExtractSetFromRepository() {
        List<Integer> listIdToTest = new ArrayList<>(List.of(1, 2, 3));

        //simulated behavior
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

        //test if method get all object from list.
        try {
            Set<Candidate> setResult = extractSetFromRepository(repository, listIdToTest);
            assertEquals(setResult.size(), listIdToTest.size());
        } catch (IdNotFoundException e) {
            fail();
        }

        //add empty reference to the list
        listIdToTest.add(8);

        //test if method throw the right exception when an empty reference is found
        assertThrows(IdNotFoundException.class, () -> extractSetFromRepository(repository, listIdToTest));
    }


}
