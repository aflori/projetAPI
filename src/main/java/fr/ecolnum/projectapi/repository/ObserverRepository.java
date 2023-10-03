package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Observer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for CRUD usage
 */
@Repository
public interface ObserverRepository extends JpaRepository<Observer, Integer> {
    Observer findByEmail(String email);
}
