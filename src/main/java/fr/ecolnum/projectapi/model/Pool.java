package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

/**
 * define structure pool and composants for the table
 */
@Entity
public class Pool {

    /**
     * join pooltable to other tables
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "startDate", nullable = false)
    private Timestamp startDate;
    @Column(name = "endDate", nullable = false)
    private Timestamp endDate;
    @Column(name = "location", nullable = false)
    private String location;
    /**
     * une piscine peut avoir plsusieurs groupes
     */
    @OneToMany(mappedBy = "belongsToPool")
       private Set<Group> containIn;

    @ManyToMany
    @JoinTable(
            name = "pool_candidate",
            joinColumns = @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private Set<Candidate> evaluates;

    @ManyToMany
    @JoinTable(
            name = "pool_criteria",
            joinColumns = @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "criteria_id")
    )
    private Set<Criteria> containedCriterias;


    @ManyToMany
    @JoinTable(
            name = "pool_observer",
            joinColumns = @JoinColumn(name = "pool_id"),
            inverseJoinColumns = @JoinColumn(name = "observer_id")
    )
    private Set<Observer> containedObservers;

    public Pool(Integer id, String name, Timestamp startDate, Timestamp endDate, String location,
                Set<Candidate> evaluates, Set<Criteria> containedCriterias, Set<Observer> containedObservers) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.evaluates = evaluates;
        this.containedCriterias = containedCriterias;
        this.containedObservers = containedObservers;
    }

    public Pool(Set<Group> containIn) {

        this.containIn = containIn;
    }

    public Pool() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Candidate> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(Set<Candidate> evaluates) {
        this.evaluates = evaluates;
    }

    public Set<Criteria> getContainedCriterias() {
        return containedCriterias;
    }

    public void setContainedCriterias(Set<Criteria> containedCriterias) {
        this.containedCriterias = containedCriterias;
    }

    public Set<Observer> getContainedObservers() {
        return containedObservers;
    }

    public void setContainedObservers(Set<Observer> containedObservers) {
        this.containedObservers = containedObservers;
    }
}
