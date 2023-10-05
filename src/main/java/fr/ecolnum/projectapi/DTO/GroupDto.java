package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.*;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;

public class GroupDto {
    private int id;
    private String name;
    private List<Integer> containedCandidates;
    private int belongsToPool;

    public GroupDto() {
    }

    public GroupDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();

        Set<Candidate> belongsTo = group.getContainedCandidates();
        containedCandidates = new ArrayList<>();

        if (belongsTo != null) {
            for (Candidate candidateList : belongsTo) {
                containedCandidates.add(candidateList.getId());
            }
        }

        Pool pool = group.getBelongsToPool();
        if (pool != null) {
            this.belongsToPool= pool.getId();
        }

    }
    public Group convertToGroupObject(final CandidateRepository candidateRepository, final PoolRepository poolRepository) throws IdNotFoundException {
        Set<Candidate> belongsTo = extractSetFromRepository(candidateRepository, containedCandidates);
        Optional<Pool> optionalBelongsToPool = poolRepository.findById(this.belongsToPool);
        if (optionalBelongsToPool.isEmpty()) {
            throw new IdNotFoundException();
        }
        Pool belongsToPool = optionalBelongsToPool.get();
        return new Group(this.id, this.name, belongsToPool , belongsTo);
    }

    public List<Integer> getContainedCandidates() {
        return containedCandidates;
    }

    public void setContainedCandidates(List<Integer> containedCandidates) {
        this.containedCandidates = containedCandidates;
    }
    public int getBelongsToPool() {
        return belongsToPool;
    }

    public void setBelongsToPool(int belongsToPool) {
        this.belongsToPool = belongsToPool;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
