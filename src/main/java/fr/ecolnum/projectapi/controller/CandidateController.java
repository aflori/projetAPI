package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.exception.CandidateAlreadyExistsException;
import fr.ecolnum.projectapi.exception.FileNotUpdatableException;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.MultipartFileIsNotImageException;
import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.model.Candidate;
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
     * @return the candidate created with Http code 201
     * @author aflori
     */

@PostMapping
    @Operation(
            summary = "Create a new candidate",
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
     * @param photoCandidate photo of checked candidate
     * @return Candidate created if candidate did not already exist, if not, a message with multiples choice http code
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

    /**
     * function to get all candidate registered
     *
     * @return a list of candidate
     */
    @Operation(
            summary = "Send the list of candidate in the database"
    )
    @ApiResponse(
            description = "list of all candidate  with the ok http response",
            responseCode = "200"
    )
    @GetMapping
    public ResponseEntity<?> getAllCandidate() {

        Iterable<?> candidateLise = candidateService.getAllCandidate();

        return new ResponseEntity<>(candidateLise, HttpStatus.OK);
    }

    /**
     * endpoint to return a specific candidate
     *
     * @param id if of the candidate we are searching for
     * @return candidate containing the good id
     */
    @Operation(
            summary = "return a specific candidate given in the Url"
    )
    @ApiResponse(
            description = "candidate of a candidate chosen by its id",
            responseCode = "200"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getCandidateById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(candidateService.getCandidateById(id), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/duplicate")
    @Operation(
            summary = "get the list of candidate with the same first name and last name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "candidate with a first name and last name",
                    required = true
            )
    )
    @ApiResponse(
            description = "list of candidate with the same first name and last name with the one given in body",
            responseCode = "200"
    )
    public ResponseEntity<?> getDuplicateNameCandidate(@RequestBody CandidateDto candidate) {
        Iterable<CandidateDto> listDuplicate = candidateService.returnDuplicate(candidate);
        return new ResponseEntity<>(listDuplicate, HttpStatus.OK);
    }

}
