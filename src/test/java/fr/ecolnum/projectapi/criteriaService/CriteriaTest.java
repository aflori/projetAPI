/*
package fr.ecolnum.projectapi.criteriaService;

import fr.ecolnum.projectapi.DTO.CriteriaDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import fr.ecolnum.projectapi.service.CriteriaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
*/

/**
@SpringBootTest
public class CriteriaTest {
    @InjectMocks
    public CriteriaService criteriaService;
    @Mock
    public CriteriaRepository criteriaRepository;
    @Mock
    public PoolRepository poolRepository;

    @Test
    public void createTest() throws IdNotFoundException {

        CriteriaDto criteriaTest = new CriteriaDto();
        criteriaTest.setId(0);
        criteriaTest.setName("dsds");
        criteriaTest.setDescription("dsd");
        when(criteriaRepository.save(criteriaTest.convertToCriteriaObject(poolRepository)))
                .thenReturn(

                        new Criteria(1,"dsds","dsd", null)

                        );

        CriteriaDto testValueChamps = criteriaService.createCriteria (criteriaTest);
        assertEquals(testValueChamps.getId(),1);
        assertEquals(testValueChamps.getName(),"dsds");
        assertEquals(testValueChamps.getDescription(),"dsd");
    }

}
*/