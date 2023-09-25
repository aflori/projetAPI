package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.service.PoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(path = "/pool")
public class PoolController {
    @Autowired
    private PoolService poolService;

    /**
     * @return all pools
     */
    @Operation(summary = "Return all pools", description = "Return the list of all the pools from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return pools list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllPools() {
        return new ResponseEntity<>(poolService.getAllPools(), HttpStatus.OK);
    }

    /**
     * @param id filter pool by id
     * @return pool by id
     */
    @Operation(summary = "Return pools by id", description = "Return pool by id from the database.")
    @GetMapping("/{id}")
    @ApiResponse(
            description = "Return pool by id and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getPoolById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(poolService.finById(id), HttpStatus.OK);
    }

    /**
     * @param pool
     * @return pool created
     */
    @Operation(summary = "Create a pool", description = "Add a new Pool object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return pools list and the OK HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createPool(@RequestBody Pool pool) {
        Pool createdPool = poolService.createPool(pool);
        return new ResponseEntity<>(createdPool, HttpStatus.CREATED);
    }

    /**
     * @param pool
     * @return pool modified
     */
    @Operation(summary = "Return modified pool", description = "Return the pool modified from the database.")
    @PutMapping
    @ApiResponse(
            description = "Return pool modified and the OK HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> modifyPool(@RequestBody Pool pool) {
        Pool modifiedPool = poolService.modifyPool(pool);
        return new ResponseEntity<>(modifiedPool, HttpStatus.CREATED);
    }
}
