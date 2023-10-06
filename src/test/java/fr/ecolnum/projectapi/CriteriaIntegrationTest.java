package fr.ecolnum.projectapi;

import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CriteriaIntegrationTest {


    @Autowired
    private CriteriaRepository criteriaRepository;

    @Test
    public void criteriaH2RepositoryTest() {
        Criteria criteria1 = new Criteria();
        criteria1.setName("name");
        criteria1.setDescription("description");
        criteriaRepository.save(criteria1);
        Criteria createdCriteria = criteriaRepository.findByName(criteria1.getName());
        assertEquals(createdCriteria.getName(),criteria1.getName());
    }
}
