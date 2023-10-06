package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
