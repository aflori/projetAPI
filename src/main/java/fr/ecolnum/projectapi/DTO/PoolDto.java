package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.*;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.GroupRepository;
import fr.ecolnum.projectapi.repository.ObserverRepository;
import java.sql.Timestamp;
import static fr.ecolnum.projectapi.util.GenericUtility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class PoolDto {
    private int id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String location;
    /**
     * create objects for the list which are integers
     */
    private List<Integer> containedCandidate;
    private List<Integer> containedCriterias;
    private List<Integer> containedObservers;
    private List<Integer> containedGroups;
    public PoolDto() {
    }

    /**
     * @param pool this construct take attributs of pool
     */
    public PoolDto(Pool pool) {
        this.id = pool.getId();
        this.name = pool.getName();
        this.startDate = pool.getStartDate();
        this.endDate = pool.getEndDate();
        this.location = pool.getLocation();
        /**
         * transform the pool list in object which contain an ArrayList
         */
        Set<Candidate> evaluate = pool.getEvaluates();
        containedCandidate = new ArrayList<>();

        Set<Observer> evaluateObserver = pool.getContainedObservers();
        containedObservers = new ArrayList<>();

        Set<Criteria> evaluateCriteria = pool.getContainedCriterias();
        containedCriterias = new ArrayList<>();

        Set<Group> containsGroups = pool.getContainsGroups();
        containedGroups = new ArrayList<>();

        if (evaluateCriteria != null) {
            for (Criteria criteriaList : evaluateCriteria) {
                containedCriterias.add(criteriaList.getId());
            }
        }
        if (evaluateObserver != null) {
            for (Observer observerList : evaluateObserver) {
                containedObservers.add(observerList.getId());
            }
        }
        if (evaluate != null) {
            for (Candidate candidateList : evaluate) {
                containedCandidate.add(candidateList.getId());
            }
        }
        if (containsGroups != null) {
            for (Group groupList : containsGroups) {
                containedCandidate.add(groupList.getId());
            }
        }
    }

    public Pool convertToPoolObject(final CandidateRepository candidateRepository,
                                    final CriteriaRepository criteriaRepository,
                                    final ObserverRepository observerRepository,
                                    final GroupRepository groupRepository)
            throws IdNotFoundException {

        Set<Candidate> containedCandidate = extractSetFromRepository(candidateRepository, this.containedCandidate);

        Set<Criteria> containedCriteria = extractSetFromRepository(criteriaRepository, this.containedCriterias);

        Set<Observer> containedObserver = extractSetFromRepository(observerRepository, this.containedObservers);

        Set<Group> containedGroup = extractSetFromRepository(groupRepository, this.containedGroups);


        return new Pool(this.id, this.name, this.startDate, this.endDate, this.location, containedGroup, containedCandidate, containedCriteria, containedObserver);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getContainedCandidate() {
        return containedCandidate;
    }

    public void setContainedCandidate(List<Integer> containedCandidate) {
        this.containedCandidate = containedCandidate;
    }

    public List<Integer> getContainedCriterias() {
        return containedCriterias;
    }

    public void setContainedCriterias(List<Integer> containedCriterias) {
        this.containedCriterias = containedCriterias;
    }

    public List<Integer> getContainedObservers() {
        return containedObservers;
    }

    public void setContainedObservers(List<Integer> containedObservers) {
        this.containedObservers = containedObservers;
    }

    public List<Integer> getContainedGroups() {
        return containedGroups;
    }

    public void setContainedGroups(List<Integer> containedGroups) {
        this.containedGroups = containedGroups;
    }
}
