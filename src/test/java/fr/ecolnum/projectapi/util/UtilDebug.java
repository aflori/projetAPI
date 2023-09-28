package fr.ecolnum.projectapi.util;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;


public class UtilDebug {

    private CandidateRepository repository;

    public Set<Candidate> testGenericUtility(List<Integer> list) {
        try {
            return extractSetFromRepository(repository, list);
        } catch (IdNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
