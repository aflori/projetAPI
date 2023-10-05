package fr.ecolnum.projectapi.controller;

import fr.ecolnum.projectapi.DTO.CandidateDto;
import fr.ecolnum.projectapi.DTO.CriteriaDto;
import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.DTO.PoolDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Candidate;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.service.GroupService;
import fr.ecolnum.projectapi.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fr.ecolnum.projectapi.util.GenericUtility.convertStringToJsonData;

@RestController
@RequestMapping("/api/admin/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Operation(summary = "Create a group", description = "Add a new Group object to the database.")
    @PostMapping
    @ApiResponse(
            description = "Return the created group and the created HTTP response",
            responseCode = "201"
    )
    public ResponseEntity<?> createGroup(@RequestBody GroupDto group) {
        GroupDto createdGroup = null;
        try {
            createdGroup = groupService.createGroup(group);
            return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> addToGroup(@RequestBody int candidateId, @PathVariable Integer groupId) {
        try {
            GroupDto group = groupService.addToGroup(candidateId, groupId);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Return all group", description = "Return the list of all the group from the database.")
    @GetMapping
    @ApiResponse(
            description = "Return group list and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getAllGroup() {
        Iterable<GroupDto> groupList = groupService.getAllGroup();
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    @Operation(summary = "Return groups by id", description = "Return group by id from the database.")
    @GetMapping("/{id}")
    @ApiResponse(
            description = "Return group by id and the OK HTTP response",
            responseCode = "200"
    )
    public ResponseEntity<?> getGroupById(@PathVariable(value = "id") int id) {
        try {
            return new ResponseEntity<>(groupService.findById(id), HttpStatus.OK);
        } catch (IdNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
