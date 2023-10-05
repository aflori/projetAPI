package fr.ecolnum.projectapi.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * plusieurs groupes peuvent appartenir a une piscine
     */
    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool belongsToPool;
    /**
     * un groupe peut avoir plusieurs candidats et un candidat peut appartenir a plusieurs groupes
     * un groupe possede une liste de candidats danc l'objet group
     */
    @ManyToMany
    @JoinTable(
            name = "group_candidate",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private Set<Candidate> containedCandidates;
    /**
     * Construct empty and with id and name
     */
    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(int id, String name, Pool belongsToPool, Set<Candidate> containedCandidates) {
        this.id = id;
        this.name = name;
        this.belongsToPool = belongsToPool;
    }

    /**
     * setter and getter
     *
     * @return
     */
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

    public Pool getBelongsToPool() {
        return belongsToPool;
    }

    public void setBelongsToPool(Pool belongsToPool) {
        this.belongsToPool = belongsToPool;
    }

    public Set<Candidate> getContainedCandidates() {
        return containedCandidates;
    }

    public void setContainedCandidates(Set<Candidate> containedCandidates) {
        this.containedCandidates = containedCandidates;
    }
}
