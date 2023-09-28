package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static fr.ecolnum.projectapi.util.GenericUtility.convertStringToJsonData;

/**
 * Class made to represent Controller on candidate operation
 */
@RestController
@RequestMapping("/api/admin/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    /**
     * function made to answer {{environement}}/api/admin/candidate Post request by creating a new candidate on database with its photo
     *
     * @param firstName      firstname of candidate
     * @param lastName       lastname of candidate
     * @param photoCandidate imported photo created by spring when receiving request
     * @return the candidate created
     * @author aflori
     */
    @PostMapping
    @Operation(
            summary = "Create a new candidate on database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "photo, first and last name of a candidate",
                    required = true
            )
    )
    @ApiResponse(
            description = "Candidate created by server",
            responseCode = "201"
    )
    public ResponseEntity<?> createCandidate(@RequestPart String firstName,
                                             @RequestPart String lastName,
                                             @RequestPart(name = "photo", required = false) MultipartFile photoCandidate) {
        try {

            CandidateDto createdCandidate = candidateService.createCandidate(firstName, lastName, photoCandidate);
            return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);

        } catch (MultipartFileIsNotImageException e) {

            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        } catch (FileNotUpdatableException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * @param firstName      firstName of checked candidate
     * @param lastName       lastName of checked candidate
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
    public ResponseEntity<?> checkDuplicate(@RequestPart String firstName,
                                            @RequestPart String lastName,
                                            @RequestPart(name = "photo", required = false) MultipartFile photoCandidate) {
        try {
            CandidateDto createdCandidate = candidateService.checkDuplicate(firstName, lastName, photoCandidate);
            return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
        } catch (CandidateAlreadyExistsException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.MULTIPLE_CHOICES);
        } catch (MultipartFileIsNotImageException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        } catch (FileNotUpdatableException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
