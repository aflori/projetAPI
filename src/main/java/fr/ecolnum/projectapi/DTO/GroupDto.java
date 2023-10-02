package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.*;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;

public class GroupDto {
    private int id;
    private String name;
    private List<Integer> containedCandidates;
    public GroupDto() {
    }
    public Group convertToGroupObject(final CandidateRepository candidateRepository) throws IdNotFoundException {
        Set<Candidate> belongsTo = extractSetFromRepository(candidateRepository, belongsTo);
        return new Group(this.id, this.name, this.belongsTo);
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
    }
    public GroupDto(int id, String name) {
        this.id = id;
        this.name = name;
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
