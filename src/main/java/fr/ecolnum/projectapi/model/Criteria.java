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
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "containedCriterias")
    private Set<Pool> existsIn;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category belongsToCategory;

    public Criteria(int id, String name, String description, Set<Pool> existsIn, Category belongsToCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.existsIn = existsIn;
        this.belongsToCategory = belongsToCategory;
    }

    public Criteria() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Pool> getExistsIn() {
        return existsIn;
    }

    public void setExistsIn(Set<Pool> existsIn) {
        this.existsIn = existsIn;
    }

    public Category getBelongsToCategory() {
        return belongsToCategory;
    }

    public void setBelongsToCategory(Category belongsToCategory) {
        this.belongsToCategory = belongsToCategory;
    }
}
