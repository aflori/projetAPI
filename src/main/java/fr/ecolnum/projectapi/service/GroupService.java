package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public GroupDto createGroup(GroupDto groupDto) throws IdNotFoundException {
        Group group = new Group(groupDto.getId(), groupDto.getName());
        group = groupRepository.save(group);
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
}
