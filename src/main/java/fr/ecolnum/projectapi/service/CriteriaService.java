package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.CriteriaDto;
import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.IdNotMatchingException;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.CategoryRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;

    public CriteriaDto createCriteria(CriteriaDto criteriaDto) throws IdNotFoundException {
        Criteria criteria = criteriaDto.convertToCriteriaObject(poolRepository, categoryRepository);

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
    public CriteriaDto modifyCriteria(int criteriaId, CriteriaDto modifiedCriteriaDTO) throws IdNotFoundException, IdNotMatchingException {
        if (criteriaId != modifiedCriteriaDTO.getId()) {
            throw new IdNotMatchingException("Criteria Id from request does not match Id from criteriaDTO.");
        }
        if (criteriaRepository.findById(criteriaId).isEmpty()) {
            throw new IdNotFoundException("This Criteria does not exist.");
        }
        Criteria modifiedCriteria = modifiedCriteriaDTO.convertToCriteriaObject(poolRepository, categoryRepository);
        modifiedCriteria = criteriaRepository.save(modifiedCriteria);
        return new CriteriaDto(modifiedCriteria);
    }
}
