package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;

/**
 * use method CRUD for pool
 */
@Service
public class PoolService {
    @Autowired
    private PoolRepository poolRepository;

    public Iterable<Pool> getAllPools() {
        return poolRepository.findAll();
    }

    public Optional<Pool> finById(int id){
        return poolRepository.findById(id);
    }

    public Pool createPool(Pool pool){
       return poolRepository.save(pool);
    }

    /**
     *
     * @param poolId recuperation id of pool
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

