package fr.ecolnum.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import java.util.Objects;
import java.util.Set;

/**
 * Class made to represent candidates
 *
 * @author aflori
 */
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    //    @Column(nullable = false)
    @JsonIgnore
    private String photoName;

    @ManyToMany(mappedBy = "evaluates")
    private Set<Pool> evaluatedIn;
    /**
     * Un candidat peut avoir plusieurs groupes / objet groupIn pour la list des groupes
     */
    @ManyToMany(mappedBy = "containedCandidates")
    private Set<Group> belongsTo;

    public Candidate() {
    }

    public Candidate(String firstName, String lastName) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.evaluatedIn = null;
        this.photoName = null;
    }


    public Candidate(int i, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = i;
    }

    public Candidate(int i, String firstName, String lastName, Set<Group> belongsTo) {
        this(firstName, lastName);
        this.id = i;
        this.belongsTo = belongsTo;
    }

    public Candidate(int id, String firstName, String lastName, String photoName, Set<Pool> evaluatedIn, Set<Group> belongsTo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoName = photoName;
        this.evaluatedIn = evaluatedIn;
        this.belongsTo = belongsTo;
    }

    public Candidate(int id, String firstName, String lastName, String photoName, Set<Pool> evaluatedIn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoName = photoName;
        this.evaluatedIn = evaluatedIn;
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

    public Set<Pool> getEvaluatedIn() {
        return evaluatedIn;
    }

    public void setEvaluatedIn(Set<Pool> evaluatedIn) {
        this.evaluatedIn = evaluatedIn;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", evaluatedIn=" + evaluatedIn +
                '}';
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Set<Group> getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Set<Group> belongsTo) {
        this.belongsTo = belongsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return Objects.equals(firstName, candidate.firstName) && Objects.equals(lastName, candidate.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
