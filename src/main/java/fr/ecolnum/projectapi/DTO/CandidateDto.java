package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class CandidateDto {
    private int id;
    private String firstname;
    private String lastname;
    private String photoUrl;
    /**
     * create an object for the list which are integers
     */
    private List<Integer> evaluatedIn;

    public CandidateDto() {
    }

    /**
     * @param candidate this construct take attributs of candidate
     */
    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.firstname = candidate.getFirstName();
        this.lastname = candidate.getLastName();
        this.photoUrl = candidate.getPhotoUrl();
        /**
         * transform the pool list in object observeIn which contain an ArrayList
         */
        Set<Pool> observeIn = candidate.getEvaluatedIn();
        evaluatedIn = new ArrayList<>();
        /**
         * just take all id contain in pool List
         */
        for (Pool poolList : observeIn) {
            evaluatedIn.add(poolList.getId());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Integer> getEvaluatedIn() {
        return evaluatedIn;
    }

    public void setEvaluatedIn(List<Integer> evaluatedIn) {
        this.evaluatedIn = evaluatedIn;
    }
}
