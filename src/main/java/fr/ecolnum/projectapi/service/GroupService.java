package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.repository.GroupRepository;
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
    public GroupDto createGroup(GroupDto groupDto) throws IdNotFoundException {
        Group group = groupDto.convertToGroupObject(candidateRepository);
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
    public Candidate addToGroup(CandidateDto candidateDto, Integer groupId) throws IdNotFoundException {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IdNotFoundException("Group not found with id: " + groupId));
        Candidate candidate = candidateDto.convertToCandidateObject();

        group.getContainedCandidates().add(candidate);

        groupRepository.save(group);

        return candidate;
    }
}
