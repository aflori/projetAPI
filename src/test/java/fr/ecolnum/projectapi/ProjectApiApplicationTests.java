package fr.ecolnum.projectapi;

import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Pool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProjectApiApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void testCreateCandidateToPool() {
		Pool pool = new Pool();
		Candidate candidate = new Candidate("Aurelien");

		// Inscrivez le participant à la piscine
		pool.getName(candidate);

		assertTrue(pool.isPresent(candidate));
	}
}
