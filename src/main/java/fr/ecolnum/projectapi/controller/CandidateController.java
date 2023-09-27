package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class made to represent Controller on candidate operation
 */
@RestController
@RequestMapping("/api/admin/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    /**
     *
     * @param firstName
     * @param lastName
     * @param photoCandidate
     * @return
     */
    @PostMapping
    @Operation(
            summary = "Create a new candidate on database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "first and last name of a candidate",
                    required = true
            )
    )
    @ApiResponse(
            description = "Candidate created by server",
            responseCode = "201"
    )
    public ResponseEntity<Candidate> createCandidate(@RequestPart String firstName,
                                                     @RequestPart String lastName,
                                                     @RequestPart(name = "photo") MultipartFile photoCandidate) {
        Candidate createdCandidate = candidateService.createCandidate(firstName, lastName, photoCandidate);

        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }


    /**
     *
     * @param firstName firstName of checked candidate
     * @param lastName lastName of checked candidate
     * @param photoCandidate
     * @return
     */
    @PostMapping("/checkDuplicate")
    @Operation(
            summary = "Check for duplicate candidate in the database then create it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "first and last name of a candidate",
                    required = true
            )
    )
    @ApiResponse(
            description = "Candidate created by server",
            responseCode = "201"
    )
    public ResponseEntity<Candidate> checkDuplicate(@RequestPart String firstName,
                                                    @RequestPart String lastName,
                                                    @RequestPart(name = "photo", required = false) MultipartFile photoCandidate) {
        try {
            Candidate createdCandidate = candidateService.checkDuplicate(firstName, lastName, photoCandidate);
            return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
        } catch (CandidateAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.MULTIPLE_CHOICES);
        }
    }
}
