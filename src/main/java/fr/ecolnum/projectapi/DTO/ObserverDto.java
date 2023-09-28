package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class ObserverDto {
    private int id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    /**
     * create an object for the list which are integers
     */
    private List<Integer> containInPool;

    /**
     * just an empty construct
     */
    public ObserverDto() {
    }

    /**
     * @param observer this construct take attributs of observer
     */
    public ObserverDto(Observer observer) {
        this.id = observer.getId();
        this.firstname = observer.getFirstName();
        this.lastname = observer.getLastName();
        this.email = observer.getEmail();
        this.password = observer.getPassword();
        /**
         * transform the pool list in object observeIn which contain an ArrayList
         */

        Set<Pool> observeIn = observer.getObserveIn();
        containInPool = new ArrayList<>();
        /**
         * just take all id contain in pool List
         */
        for (Pool poolList : observeIn) {
            containInPool.add(poolList.getId());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}