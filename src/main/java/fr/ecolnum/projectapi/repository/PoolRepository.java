package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface use crud by extends
 */@Repository
public interface PoolRepository extends JpaRepository<Pool, Integer> {
}
