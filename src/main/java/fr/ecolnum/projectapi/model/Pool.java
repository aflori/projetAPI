package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;

import java.sql.Timestamp;

/**
 * define structure pool and composants for the table
 */
@Entity
@Table(name = "pool")
public class Pool {
    public set<Candidate> getTablepoolcandidate() {
        return tablepoolcandidate;
    }

    public void setTablepoolcandidate(set<Candidate> tablepoolcandidate) {
        this.tablepoolcandidate = tablepoolcandidate;
    }

    public set<Criteria> getTablepoolcriteria() {
        return tablepoolcriteria;
    }

    public void setTablepoolcriteria(set<Criteria> tablepoolcriteria) {
        this.tablepoolcriteria = tablepoolcriteria;
    }

    public set<Observer> getTablepoolobserver() {
        return tablepoolobserver;
    }

    public void setTablepoolobserver(set<Observer> tablepoolobserver) {
        this.tablepoolobserver = tablepoolobserver;
    }

    @ManyToMany
    @JoinTable
    private set<Candidate> tablepoolcandidate;
    @ManyToMany
    @JoinTable
    private set <Criteria> tablepoolcriteria;
    @ManyToMany
    @JoinTable
    private set <Observer> tablepoolobserver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "startDate",nullable = false)
    private Timestamp startDate;
    @Column(name = "endDate",nullable = false)
    private Timestamp endDate;
    @Column(name = "location",nullable = false)
    private String location;
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
