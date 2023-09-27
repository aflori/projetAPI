package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObserverDto {
    private int id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private List<Integer> containInPool;
    public ObserverDto() {
    }
    public ObserverDto(Observer observer) {
        this.id = observer.getId();
        this.firstname = observer.getFirstName();
        this.lastname = observer.getLastName();
        this.email = observer.getEmail();
        this.password = observer.getPassword();

        Set<Pool> observeIn = observer.getObserveIn();
        containInPool = new ArrayList<>();

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