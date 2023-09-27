package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.PoolNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * use method CRUD for pool
 */
@Service
public class PoolService {
    @Autowired
    private PoolRepository poolRepository;

    public Iterable<PoolDto> getAllPools() {
        Set<PoolDto> allPoolAvailable = new HashSet<>();
        Iterable<Pool> poolList = poolRepository.findAll();

        for (Pool pool : poolList) {
            PoolDto poolToAdd = new PoolDto(pool);
            allPoolAvailable.add(poolToAdd);
        }

        return allPoolAvailable;
    }

    public PoolDto finById(int id) throws PoolNotFoundException {
        Optional<Pool> optionnalPool = poolRepository.findById(id);

        if (optionnalPool.isEmpty()) {
            throw new PoolNotFoundException("Id do not exist");
        }

        final Pool pool = optionnalPool.get();
        return new PoolDto(pool);
    }

    public Pool createPool(Pool pool) {
        return poolRepository.save(pool);
    }

    public PoolDto createPool(PoolDto pool) {

        return pool;
    }
    /**
     * @param poolId           recuperation id of pool
     * @param poolModification add to pools an observer or a criteria or candidate with poolId
     */
    public void modifyPool(int poolId, Pool poolModification) {
        Pool modifiedPool = poolRepository.getReferenceById(poolId);

        Set<Candidate> candidateSet = poolModification.getEvaluates();
        if (candidateSet != null) {
            for (Candidate candidate : poolModification.getEvaluates()) {
                modifiedPool.getEvaluates().add(candidate);
            }
        }
        Set<Observer> observerSet = poolModification.getContainedObservers();
        if (observerSet != null) {
            for (Observer observer : poolModification.getContainedObservers()) {
                modifiedPool.getContainedObservers().add(observer);
            }
        }
        Set<Criteria> criteriaSet = poolModification.getContainedCriterias();
        if (criteriaSet != null) {
            for (Criteria criteria : criteriaSet) {
                modifiedPool.getContainedCriterias().add(criteria);
            }
        }
        poolRepository.save(modifiedPool);
    }
}

