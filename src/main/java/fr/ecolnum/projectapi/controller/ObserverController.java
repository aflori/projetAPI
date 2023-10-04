package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.ObserverDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.exception.NameNotFoundException;
import fr.ecolnum.projectapi.model.Role;
import fr.ecolnum.projectapi.service.ObserverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

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
        } catch (IdNotFoundException|NameNotFoundException e) {
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

    @Operation(summary = "Change authorities of a user", description = "Add or remove role of an observer in the database")
    @PutMapping("/{id}/authorities")
    @ApiResponse(
            description = "Return the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> changeAuthorities(@PathVariable int id, @RequestBody Set<Role> roleSet) {
        try {
            observerService.changeAuthorities(id, roleSet);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IdNotFoundException|NameNotFoundException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @Operation(
            summary = "return a specific observer given in the Url"
    )
    @ApiResponse(
            description = "DTO of an observer chosen by its id",
            responseCode = "200"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getObserverById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(observerService.getObserverById(id), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(convertStringToJsonData(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
