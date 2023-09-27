package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PoolDto {
    private int id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String location;
    private List<Integer> containedCandidate;
    private List <Integer> containedCriterias;
    private List <Integer> containedObservers;

    public PoolDto() {
    }

    public PoolDto(Pool pool) {
        this.id = pool.getId();
        this.name = pool.getName();
        this.startDate = pool.getStartDate();
        this.endDate = pool.getEndDate();
        this.location = pool.getLocation();

        Set<Candidate> evaluate = pool.getEvaluates();
        containedCandidate = new ArrayList<>();

        Set<Observer> evaluateObserver = pool.getContainedObservers();
        containedObservers = new ArrayList<>();

        Set<Criteria> evaluateCriteria = pool.getContainedCriterias();
        containedCriterias = new ArrayList<>();

        for (Criteria criteriaList : evaluateCriteria) {
            containedCriterias.add(criteriaList.getId());
        }

        for (Observer observerList : evaluateObserver) {
            containedObservers.add(observerList.getId());
        }
        for (Candidate candidateList : evaluate) {
            containedCandidate.add(candidateList.getId());
        }
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
}
