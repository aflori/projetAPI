package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;

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
    @Column(name = "startDate",nullable = false)
    private Timestamp startDate;
    @Column(name = "endDate",nullable = false)
    private Timestamp endDate;
    @Column(name = "location",nullable = false)
    private String location;

    @ManyToMany
    @JoinTable (
            name = "pool_candidate",
            joinColumns = @JoinColumn(name="pool_id"),
            inverseJoinColumns = @JoinColumn(name ="candidate_id")
    )
    private Set <Candidate> evaluates;

    @ManyToMany
    @JoinTable (
            name = "pool_criteria",
            joinColumns = @JoinColumn(name="pool_id"),
            inverseJoinColumns = @JoinColumn(name ="criteria_id")
    )
    private Set <Criteria> containedCriterias;


    @ManyToMany
    @JoinTable (
            name = "pool_observer",
            joinColumns = @JoinColumn(name="pool_id"),
            inverseJoinColumns = @JoinColumn(name ="observer_id")
    )
    private Set <Observer> containedObservers;

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
}
