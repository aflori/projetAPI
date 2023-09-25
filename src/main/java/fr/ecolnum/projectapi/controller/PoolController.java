package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.service.PoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * path for CRUD pool
 */
@Controller
@RequestMapping(path="/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;

    /**
     *
     * @return all pools
     */
    @GetMapping
    public ResponseEntity<?> getAllPools() {
        return new ResponseEntity<>(poolService.getAllPools(), HttpStatus.OK);
    }

    /**
     *
     * @param id filter pool by id
     * @return pool by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPoolById(@PathVariable(value = "id") int id){
        return new ResponseEntity<>(poolService.finById(id), HttpStatus.OK);
    }

    /**
     *
     * @param pool
     * @return pool created
     */
    @PostMapping
    public ResponseEntity<?> createPool(@RequestBody Pool pool) {
       Pool createdPool = poolService.createPool(pool);
        return new ResponseEntity<>(createdPool, HttpStatus.CREATED);
    }

    /**
     *
     * @param pool
     * @return pool modified
     */
     @PutMapping
     public ResponseEntity<?> modifyPool(@RequestBody Pool pool) {
         Pool modifiedPool = poolService.modifyPool(pool);
         return new ResponseEntity<>(modifiedPool, HttpStatus.CREATED);
     }
}
