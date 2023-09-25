package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * This class will be the criteria model used to evaluate candidates
 */
@Entity
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "containedCriterias")
    private Set<Criteria> existsIn;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Criteria> getExistsIn() {
        return existsIn;
    }

    public void setExistsIn(Set<Criteria> existsIn) {
        this.existsIn = existsIn;
    }
}
