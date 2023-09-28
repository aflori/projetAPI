package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.CriteriaDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains the methods regarding the Criteria objects.
 */
@Service
public class CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private PoolRepository poolRepository;

    public CriteriaDto createCriteria(CriteriaDto criteriaDto) throws IdNotFoundException {
        Criteria criteria = criteriaDto.convertToCriteriaObject(poolRepository);

        criteria = criteriaRepository.save(criteria);

        return new CriteriaDto(criteria);
    }

    public Iterable<CriteriaDto> getAllCriterias() {
        Iterable<Criteria> allCriteria = criteriaRepository.findAll();
        Set<CriteriaDto> allCriteriaDto = new HashSet<>();

        for (Criteria criteria : allCriteria) {
            allCriteriaDto.add(new CriteriaDto(criteria));
        }

        return allCriteriaDto;
    }

}
