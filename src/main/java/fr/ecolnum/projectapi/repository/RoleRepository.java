package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
