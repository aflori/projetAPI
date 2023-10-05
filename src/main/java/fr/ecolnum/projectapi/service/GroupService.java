package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.repository.GroupRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.ecolnum.projectapi.repository.CandidateRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private PoolRepository poolRepository;

    public GroupDto createGroup(GroupDto groupDto) throws IdNotFoundException {
        Group group = groupDto.convertToGroupObject(candidateRepository, poolRepository);
        group = groupRepository.save(group);
        return new GroupDto(group);
    }

    public GroupDto findById(int id) throws IdNotFoundException {
        Optional<Group> optionnalGroup = groupRepository.findById(id);

        if (optionnalGroup.isEmpty()) {
            throw new IdNotFoundException("group id not found");
        }

        final Group group = optionnalGroup.get();
        return new GroupDto(group);
    }

    public Iterable<GroupDto> getAllGroup() {
        Iterable<Group> allGroup = groupRepository.findAll();
        Set<GroupDto> allGroupDto = new HashSet<>();

        for (Group group : allGroup) {
            allGroupDto.add(new GroupDto(group));
        }
        return allGroupDto;
    }

    public GroupDto addToGroup(int candidateId, Integer groupId) throws IdNotFoundException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IdNotFoundException("Group not found with id: " + groupId));
        Optional<Candidate> optionalCandidate = candidateRepository.findById(candidateId);

        if (optionalCandidate.isEmpty()) {
            throw new IdNotFoundException("Candidate not found with id: " + candidateId);
        }

        group.getContainedCandidates().add(optionalCandidate.get());

        group = groupRepository.save(group);

        return new GroupDto(group);
    }
}
