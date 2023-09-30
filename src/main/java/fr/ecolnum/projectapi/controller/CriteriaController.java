package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.CriteriaDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.service.CriteriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fr.ecolnum.projectapi.util.GenericUtility.convertStringToJsonData;

/**
 * This class manages the http requests of the Criteria objects.
 */
@RestController
@RequestMapping("/api/admin/criteria")
@CrossOrigin
public class CriteriaController {
    @Autowired
    private CriteriaService criteriaService;

    @Operation(summary = "Create a criteria", description = "Add a new Criteria object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return the created criteria and the created HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createCriteria(@RequestBody CriteriaDto criteria) {

        try {
            CriteriaDto createdCriteria = criteriaService.createCriteria(criteria);
            return new ResponseEntity<>(createdCriteria, HttpStatus.CREATED);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Return all criterias", description = "Return the list of all the criterias from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return criterias list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllCriterias() {
        Iterable<CriteriaDto> criteriaList = criteriaService.getAllCriterias();
        return new ResponseEntity<>(criteriaList, HttpStatus.OK);
    }


}
