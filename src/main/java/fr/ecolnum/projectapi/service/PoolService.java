package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * use CRUD for pool
 */
@Service
public class PoolService {
    @Autowired
    private PoolRepository poolRepository;

    public Iterable<Pool> findAll() {
        return poolRepository.findAll();
    }

    public Optional<Pool> finById(int id){
        return poolRepository.findById(id);
    }

    public Pool save(Pool pool){
        return poolRepository.save(pool);
    }

    public String delete( Pool pool){
        poolRepository.delete(pool);
        return "Succesfully Deleted";
    }

    public String update(Pool pool){
        poolRepository.save(pool);
        return "succesfully modify";
    }
}

