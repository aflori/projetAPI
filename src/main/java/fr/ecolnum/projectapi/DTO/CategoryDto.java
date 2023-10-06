package fr.ecolnum.projectapi.DTO;


import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.*;
import fr.ecolnum.projectapi.repository.CandidateRepository;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;

public class CategoryDto {
    private int id;
    private String name;
    private List<Integer> containedCriterias;
    public CategoryDto(){
    }
    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();

        Set<Criteria> belongsToCategory = category.getContainsCriterias();
        containedCriterias = new ArrayList<>();

        if (belongsToCategory != null) {
            for (Criteria criteriaList : belongsToCategory) {
                containedCriterias.add(criteriaList.getId());
            }
        }
    }
    public Category convertToCategoryObject(final CriteriaRepository criteriaRepository) throws IdNotFoundException {
        Set<Criteria> belongsToCategory = extractSetFromRepository(criteriaRepository, containedCriterias);
        return new Category(this.id, this.name, belongsToCategory);
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

    public List<Integer> getContainedCriterias() {
        return containedCriterias;
    }

    public void setContainedCriterias(List<Integer> containedCriterias) {
        this.containedCriterias = containedCriterias;
    }
}
