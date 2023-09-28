package fr.ecolnum.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

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
    private String photoUrl;

    @ManyToMany(mappedBy = "evaluates")
    private Set<Pool> evaluatedIn;

    public Candidate() {
    }

    public Candidate(String firstName, String lastName) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.evaluatedIn = null;
        this.photoUrl = null;
    }


    public Candidate(int i, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = i;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
