package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "belongsToCategory")
    Set<Criteria> containsCriterias;
    public Category(){
    }
    public Category(int id, String name, Set<Criteria> containsCriterias) {
        this.id = id;
        this.name = name;
        this.containsCriterias = containsCriterias;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Criteria> getContainsCriterias() {
        return containsCriterias;
    }

    public void setContainsCriterias(Set<Criteria> containsCriterias) {
        this.containsCriterias = containsCriterias;
    }
}
