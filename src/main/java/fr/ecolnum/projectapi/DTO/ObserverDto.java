package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.PoolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;


/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class ObserverDto {
    private int id;
    private String firstName;
    private String lastName;
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
        this.firstName = observer.getFirstName();
        this.lastName = observer.getLastName();
        this.email = observer.getEmail();
        this.password = observer.getPassword();
        /*
         * transform the pool list in object observeIn which contain an ArrayList
         */

        Set<Pool> observeIn = observer.getObserveIn();
        containInPool = new ArrayList<>();
        /*
         * just take all id contain in pool List
         */
        if(observeIn != null) {
            for (Pool poolList : observeIn) {
                containInPool.add(poolList.getId());
            }
        }
    }

    public ObserverDto(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Observer convertToObserverObject(final PoolRepository poolRepository) throws IdNotFoundException {
        Set<Pool> observeIn = extractSetFromRepository(poolRepository, this.containInPool);
        return new Observer(this.id, this.lastName, this.firstName, this.email, this.password, observeIn);
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

    public List<Integer> getContainInPool() {
        return containInPool;
    }

    public void setContainInPool(List<Integer> containInPool) {
        this.containInPool = containInPool;
    }
}