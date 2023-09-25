package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Pool modifyPool(Pool pool){
       return poolRepository.save(pool);
    }
    public String addObserverToPool(Observer){
        return null;
    }
}

