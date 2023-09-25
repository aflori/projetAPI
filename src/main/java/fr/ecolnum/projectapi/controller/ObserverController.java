package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.service.ObserverService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ObserverController {
    @Autowired
    private ObserverService observerService;

    @Operation(summary = "Create an observer", description = "Add a new Observer object to the database.")
    @PostMapping("/observer")
    public ResponseEntity<?> createObserver(@RequestBody Observer observer){

        observerService.createObserver(observer);

        return new ResponseEntity<>(observer, HttpStatus.CREATED);
    }

    @Operation(summary = "Return all observers", description = "Return the list of all the observers from the database.")
    @GetMapping("/observers")
    public ResponseEntity<?> getAllObservers() {
        return new ResponseEntity<>(observerService.getAllObservers(), HttpStatus.OK);
    }
}
