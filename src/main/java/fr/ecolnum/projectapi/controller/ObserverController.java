package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.service.ObserverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fr.ecolnum.projectapi.util.GenericUtility.convertStringToJsonData;

/**
 * This class manages the http requests of the Observer objects.
 */
@RestController
@RequestMapping("/api/admin/observer")
public class ObserverController {
    @Autowired
    private ObserverService observerService;

    @Operation(summary = "Create an observer", description = "Add a new Observer object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return the created observer and the created HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createObserver(@RequestBody ObserverDto observer) {


        try {
            ObserverDto createdObserver = observerService.createObserver(observer);
            return new ResponseEntity<>(createdObserver, HttpStatus.CREATED);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "Return all observers", description = "Return the list of all the observers from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return observers list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllObservers() {
        Iterable<ObserverDto> observersList = observerService.getAllObservers();
        return new ResponseEntity<>(observersList, HttpStatus.OK);
    }
}
