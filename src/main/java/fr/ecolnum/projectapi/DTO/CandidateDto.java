package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CandidateDto {
    private int id;
    private String firstname;
    private String lastname;
    private String photoUrl;
    private List<Integer> evaluatedIn;

    public CandidateDto() {
    }
    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.firstname = candidate.getFirstName();
        this.lastname = candidate.getLastName();
        this.photoUrl = candidate.getPhotoUrl();


        Set<Pool> observeIn = candidate.getEvaluatedIn();
        evaluatedIn = new ArrayList<>();

        for (Pool poolList : observeIn) {
            evaluatedIn.add(poolList.getId());
        }
    }
}
