package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class contains the methods regarding the Criteria objects.
 */
@Service
public class CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    public Criteria createCriteria(Criteria criteria) {
        return criteriaRepository.save(criteria);
    }

    public Iterable<Criteria> getAllCriterias() {
        return criteriaRepository.findAll();
    }

}
