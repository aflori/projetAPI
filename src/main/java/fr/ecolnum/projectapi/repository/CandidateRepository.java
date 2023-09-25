package fr.ecolnum.projectapi.repository;

import fr.ecolnum.projectapi.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
}