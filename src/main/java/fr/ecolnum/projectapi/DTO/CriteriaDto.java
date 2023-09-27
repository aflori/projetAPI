package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Observer;
import fr.ecolnum.projectapi.model.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CriteriaDto {
    private int id;
    private String name;
    private String description;

    private List<Integer> existInPool;
    public CriteriaDto() {
    }
    public CriteriaDto(Criteria criteria) {
        this.id = criteria.getId();
        this.name = criteria.getName();
        this.description = criteria.getDescription();

        Set<Pool> existsIn = criteria.getExistsIn();
        existInPool = new ArrayList<>();

        for (Pool poolList : existsIn) {
            existInPool.add(poolList.getId());
        }
    }
}
