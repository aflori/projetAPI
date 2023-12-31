package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.IdNotMatchingException;
import fr.ecolnum.projectapi.service.PoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * path for CRUD pool
 */
@Controller
@RequestMapping(path = "/api/admin/pool")
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
        try {
            return new ResponseEntity<>(poolService.findById(id), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param pool DtoObject with necessary data for pool creation
     * @return pool created
     */
    @Operation(summary = "Create a pool", description = "Add a new Pool object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return pools list and the OK HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createPool(@RequestBody PoolDto pool) {
        PoolDto createdPool;
        try {
            createdPool = poolService.createPool(pool);
            return new ResponseEntity<>(createdPool, HttpStatus.CREATED);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "add a group of candidate to a pool",
            description = "add a group of candidate to the pool corresponding to the URL id (before group) ")
    @PutMapping("/{id}/group")
    @ApiResponse(
            description = "Return the list of group added to pool.",
            responseCode = "201"
    )
    public ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto, @PathVariable int id) {
        GroupDto addedGroup;
        try {
            addedGroup = poolService.addGroup(groupDto, id);
            return new ResponseEntity<>(addedGroup, HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * @param poolId           id of the pool which will be modified
     * @param poolModification it's pool's modification with her id / we add a new object like (candidate, Criteria, observer)
     * @return return just a status
     */
    @Operation(summary = "Return modified pool", description = "Return the pool modified from the database.")
    @PutMapping("/{poolId}")
    @ApiResponse(
            description = "Return pool modified and the OK HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> modifyPool(@PathVariable int poolId, @RequestBody PoolDto poolModification) {
        PoolDto poolModified;
        try {
            poolModified = poolService.modifyPool(poolId, poolModification);
            return new ResponseEntity<>(poolModified, HttpStatus.OK);
        } catch (IdNotFoundException | IdNotMatchingException e) {
            return new ResponseEntity<>("{\"Error\" :\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
        }
    }

}
