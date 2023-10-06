package fr.ecolnum.projectapi.service;

import fr.ecolnum.projectapi.DTO.CategoryDto;
import fr.ecolnum.projectapi.DTO.GroupDto;
import fr.ecolnum.projectapi.exception.IdNotFoundException;
import fr.ecolnum.projectapi.model.Category;
import fr.ecolnum.projectapi.model.Group;
import fr.ecolnum.projectapi.repository.CategoryRepository;
import fr.ecolnum.projectapi.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CriteriaRepository criteriaRepository;
    public CategoryDto createCategory(CategoryDto categoryDto) throws IdNotFoundException {
        Category category = categoryDto.convertToCategoryObject(criteriaRepository);
        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }
    public Iterable<CategoryDto> getAllCategory() {
        Iterable<Category> allCategory = categoryRepository.findAll();
        Set<CategoryDto> allCategoryDto = new HashSet<>();

        for (Category category : allCategory) {
            allCategoryDto.add(new CategoryDto(category));
        }
        return allCategoryDto;
    }
}
