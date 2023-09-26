package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Pool;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface use crud by extends
 */
public interface PoolRepository extends JpaRepository<Pool,Integer> {
}
