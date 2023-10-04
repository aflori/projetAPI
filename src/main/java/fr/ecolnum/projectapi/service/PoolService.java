package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.IdNotMatchingException;
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

    public PoolDto createPool(PoolDto poolDTO) throws IdNotFoundException {

        Pool pool = poolDTO.convertToPoolObject(candidateRepository, criteriaRepository, observerRepository);
        pool = poolRepository.save(pool);
        return new PoolDto(pool);
    }


    public PoolDto modifyPool(int poolId, PoolDto modifiedPoolDTO) throws IdNotFoundException, IdNotMatchingException {
        if (poolId != modifiedPoolDTO.getId()) {
            throw new IdNotMatchingException("Pool Id from request does not match Id from poolDTO.");
        }
        if (poolRepository.findById(poolId).isEmpty()) {
            throw new IdNotFoundException("This Pool does not exist.");
        }
        Pool modifiedPool = modifiedPoolDTO.convertToPoolObject(candidateRepository, criteriaRepository, observerRepository);
        modifiedPool = poolRepository.save(modifiedPool);
        return new PoolDto(modifiedPool);
    }
}

