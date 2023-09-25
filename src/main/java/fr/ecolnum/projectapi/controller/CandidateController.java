package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class made to represent Controller on candidate operation
 */
@RestController
@RequestMapping("/api/admin/candidate")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate createdCandidate = service.createCandidate(candidate);

        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }
}
