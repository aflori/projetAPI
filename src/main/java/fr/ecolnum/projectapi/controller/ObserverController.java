package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.service.ObserverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createObserver(@RequestBody Observer observer){

        Observer createdObserver =  observerService.createObserver(observer);

        return new ResponseEntity<>(createdObserver, HttpStatus.CREATED);
    }

    @Operation(summary = "Return all observers", description = "Return the list of all the observers from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return observers list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllObservers() {
        Iterable<Observer> observersList = observerService.getAllObservers();
        return new ResponseEntity<>(observersList, HttpStatus.OK);
    }
}
