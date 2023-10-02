package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Group;

public class GroupDto {
    private int id;
    private String name;

    public GroupDto() {
    }

    ;

    public GroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
    }

    public GroupDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
