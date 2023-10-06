package fr.ecolnum.projectapi.DTO;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Category;
import fr.ecolnum.projectapi.model.Criteria;
import fr.ecolnum.projectapi.model.Pool;
import fr.ecolnum.projectapi.repository.CategoryRepository;
import fr.ecolnum.projectapi.repository.PoolRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static fr.ecolnum.projectapi.util.GenericUtility.extractSetFromRepository;


/**
 * DTO is a pattern (data transfert object) which use for the recursivity
 * DTO take only the id for the pools which associated no more
 */
public class CriteriaDto {
    private int id;
    private String name;
    private String description;
    /**
     * create an object for the list which are integers
     */
    private List<Integer> existInPool;
    private Integer belongsToCategory;

    public CriteriaDto() {
    }

    /**
     * @param criteria this construct take attributs of criteria
     */
    public CriteriaDto(Criteria criteria) {
        this.id = criteria.getId();
        this.name = criteria.getName();
        this.description = criteria.getDescription();
        this.belongsToCategory = criteria.getBelongsToCategory().getId();
        /**
         * transform the pool list in object existsIn which contain an ArrayList
         */
        Set<Pool> existsIn = criteria.getExistsIn();
        existInPool = new ArrayList<>();
        /**
         * just take all id contain in pool List
         */
        if (existsIn != null) {
            for (Pool poolList : existsIn) {
                existInPool.add(poolList.getId());
            }
        }
    }

    public Criteria convertToCriteriaObject(final PoolRepository poolRepository, final CategoryRepository categoryRepository) throws IdNotFoundException {
        Set<Pool> existsIn = extractSetFromRepository(poolRepository, this.existInPool);
        Optional<Category> belongsToCategory = categoryRepository.findById(this.belongsToCategory);
        if (belongsToCategory.isEmpty()){
            throw new IdNotFoundException("This category doesnt exist, Id : "+this.belongsToCategory) ;
        }
        return new Criteria(this.id, this.name, this.description, existsIn, belongsToCategory.get());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getExistInPool() {
        return existInPool;
    }

    public void setExistInPool(List<Integer> existInPool) {
        this.existInPool = existInPool;
    }

    public Integer getBelongsToCategory() {
        return belongsToCategory;
    }

    public void setBelongsToCategory(Integer belongsToCategory) {
        this.belongsToCategory = belongsToCategory;
    }
}
