package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for CRUD usage
 */
@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {
    Criteria findByName(String name);
}
