package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.ObserverRepository;
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

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private ObserverRepository observerRepository;

    public Iterable<PoolDto> getAllPools() {
        Set<PoolDto> allPoolAvailable = new HashSet<>();
        Iterable<Pool> poolList = poolRepository.findAll();

        for (Pool pool : poolList) {
            PoolDto poolToAdd = new PoolDto(pool);
            allPoolAvailable.add(poolToAdd);
        }

        return allPoolAvailable;
    }

    public PoolDto finById(int id) throws IdNotFoundException {
        Optional<Pool> optionnalPool = poolRepository.findById(id);

        if (optionnalPool.isEmpty()) {
            throw new IdNotFoundException("pool id not found");
        }

        final Pool pool = optionnalPool.get();
        return new PoolDto(pool);
    }

      public PoolDto createPool(PoolDto pool) throws IdNotFoundException {

        Pool poolDB = pool.convertToPoolObject(candidateRepository,criteriaRepository,observerRepository);
        poolRepository.save(poolDB);

        PoolDto poolCreated = new PoolDto(poolDB);
        return poolCreated;
    }
    /**
     * @param poolId           recuperation id of pool
     * @param poolModification add to pools an observer or a criteria or candidate with poolId
     * @return
     */
    public Pool modifyPool(int poolId, Pool poolModification) {
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
        return poolRepository.save(modifiedPool);
    }

    public PoolDto modifyPool(int poolId, PoolDto linksToRegister) throws IdNotFoundException {
        Pool links = linksToRegister.convertToPoolObject(candidateRepository,criteriaRepository,observerRepository);

        Pool objectCreated = modifyPool(poolId, links);

        return  new PoolDto(objectCreated);
    }
}

