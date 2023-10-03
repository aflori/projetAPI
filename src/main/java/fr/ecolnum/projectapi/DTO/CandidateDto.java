package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.GroupRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;

/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class CandidateDto {
    private int id;
    private String firstName;
    private String lastName;
    private String photoName;
    /**
     * create an object for the list which are integers
     */
    private List<Integer> evaluatedIn;
    private List<Integer> belongsToPool;


    /**
     * @param candidate this construct take attributs of candidate
     */
    public CandidateDto(Candidate candidate) {
        this.id = candidate.getId();
        this.firstName = candidate.getFirstName();
        this.lastName = candidate.getLastName();
        this.photoName = candidate.getPhotoName();
        /**
         * transform the pool list in object observeIn which contain an ArrayList
         */
        Set<Pool> observeIn = candidate.getEvaluatedIn();
        evaluatedIn = new ArrayList<>();
        /**
         * just take all id contain in pool List
         */
        if (observeIn != null) {
            for (Pool poolList : observeIn) {
                evaluatedIn.add(poolList.getId());
            }
        }
    }

    public Candidate convertToCandidateObject(final GroupRepository groupRepository) throws IdNotFoundException {
        Set<Group> belongsTo = extractSetFromRepository(groupRepository, this.belongsToPool);

        return new Candidate(this.id, this.firstName, this.lastName, belongsTo);
    }
    public CandidateDto(String firstName, String lastName) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public CandidateDto(String firstName, String lastName,List<Integer> belongsToPool) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.belongsToPool = belongsToPool;
    }
    public Candidate convertToCandidateObject(final PoolRepository poolRepository) throws IdNotFoundException {

        Set<Pool> evaluatedIn = extractSetFromRepository(poolRepository, this.evaluatedIn);

        return new Candidate(this.id, this.firstName, this.lastName, this.photoName, evaluatedIn);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public List<Integer> getEvaluatedIn() {
        return evaluatedIn;
    }

    public void setEvaluatedIn(List<Integer> evaluatedIn) {
        this.evaluatedIn = evaluatedIn;
    }
}
