package fr.ecolnum.projectapi.poolTest;

import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import fr.ecolnum.projectapi.service.ObserverService;
import fr.ecolnum.projectapi.service.PoolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PoolServiceTest {

    @InjectMocks
    private PoolService poolService;

    @Mock
    private PoolRepository poolRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private CriteriaRepository criteriaRepository;
    @Mock
    private ObserverService observerService;

    @Mock
    private PoolDto poolDto;


    @Test
    public void testGetAllPools(){

    }

    @Test
    public  void testFindByIdNoException() {

    }

    @Test
    public void testFindByIdWithException(){

    }
    @Test
    public void testCreatePoolNoException() {

    }

    @Test
    public void testCreatePoolWithException() {

    }

    @Test
    public void testModifyPoolNoException(){

    }

    @Test
    public void testModifyPoolIdNotFoundException(){

    }

    @Test
    public void testModifyPoolPoolNotMatchingException() {

    }

}
