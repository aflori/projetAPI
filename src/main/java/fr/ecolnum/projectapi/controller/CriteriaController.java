package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.service.CriteriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class manages the http requests of the Criteria objects.
 */
@RestController
@RequestMapping("/api/admin/criteria")
public class CriteriaController {
    @Autowired
    private CriteriaService criteriaService;

    @Operation(summary = "Create a criteria", description = "Add a new Criteria object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return the created criteria and the created HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createCriteria(@RequestBody Criteria criteria){

        Criteria createdCriteria =  criteriaService.createCriteria(criteria);

        return new ResponseEntity<>(createdCriteria, HttpStatus.CREATED);
    }

    @Operation(summary = "Return all criterias", description = "Return the list of all the criterias from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return criterias list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllCriterias() {
        return new ResponseEntity<>(criteriaService.getAllCriterias(), HttpStatus.OK);
    }


}
